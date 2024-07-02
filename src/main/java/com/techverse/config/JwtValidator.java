package com.techverse.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.techverse.service.CustomUserServiceImplementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys; 
@Component
public class JwtValidator extends OncePerRequestFilter {

	/*@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 
		
		String jwt=request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt!=null)
		{
			jwt=jwt.substring(7);
			try {
				SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String email=String.valueOf(claims.get("email"));
				
				String authorities=String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, auths);
				
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			}
			catch (Exception e) {
				// TODO: handle exception
				
				throw new BadCredentialsException("Invalid token from jwt validator");
			} 
				 
		}
		filterChain.doFilter(request, response);
		
	}
	
	*/
	 @Autowired
	    private JwtProvider tokenProvider;

	    @Autowired
	    private  CustomUserServiceImplementation customUserDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	        String jwt = getJwtFromRequest(request);

	        if (jwt != null && tokenProvider.validateToken(jwt)) {
	        	System.out.println( tokenProvider.validateToken(jwt)+"   " +jwt);
	            Long userId =Long.parseLong(tokenProvider.getUserIdFromJWT(jwt));
	            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }

	    private String getJwtFromRequest(HttpServletRequest request) {
	        String bearerToken = request.getHeader("Authorization");
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	        	System.out.println(bearerToken);
	            return bearerToken.substring(7);
	        }
	        return null;
	    }

}
