package com.techverse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.config.JwtProvider;
import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.model.ShippingAddress;
import com.techverse.model.User;
import com.techverse.repository.ProductRepository;
import com.techverse.repository.ShippingAddressRepository;
import com.techverse.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private ShippingAddressRepository shippingAddressRepository;

	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
			return user.get();
		throw new UserException("User Not Found with id  "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
	  String t=jwt.substring(7);
	 	
	 Long Id=Long.parseLong(jwtProvider.getUserIdFromJWT(t));
		
		Optional<User> user=userRepository.findById(Id);
		if(user.isEmpty())
		{
			throw new UserException("User Not Found with email  "+user.get().getEmail());
		}
		return user.get();
	}
	
	@Override
    public User addFavoriteProduct(Long userId, Long productId)  throws ProductException{
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Product> product = productRepository.findById(productId);// Implement this method to get the product from repository
        		
        if(product.isEmpty())	{
        	throw new ProductException("No product found");
        }
        if (user.getFavoriteProducts().contains(product.get())) {
            // Product already exists in favorites, handle this case as per your requirements
            // For example, throw an exception, log a message, or return the user as-is
            return user;
        }
        
        user.getFavoriteProducts().add(product.get());
        return userRepository.save(user);
    }
	
	
	@Override
    public User deleteFavoriteProduct(Long userId, Long productId) throws UserException, ProductException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));

        if (!user.getFavoriteProducts().contains(product)) {
            throw new ProductException("Product not found in user's favorites");
        }

        user.getFavoriteProducts().remove(product);
        userRepository.save(user);

        return user;
    }

    @Override
    public List<Product> getFavoriteProducts(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        return user.getFavoriteProducts();
    }

    @Override
    public ShippingAddress getDefaultShippingAddress(User user) {
        return shippingAddressRepository.findByUserAndSetDefaultAddress(user, true);
    }
    

}
