package com.techverse.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.model.Category;
import com.techverse.service.CategoryService;
import com.techverse.service.StorageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private StorageService storageService;

    @PostMapping
    public ResponseEntity<Map<String, Object>>  createCategory(@RequestPart String name,@RequestPart MultipartFile blacklogo,@RequestPart MultipartFile whitelogo,@RequestPart MultipartFile image) {
       Category category=new Category();
       category.setCategoryName(name);
       if(blacklogo!=null)
       {
    	   String path=storageService.uploadFileOnAzure(blacklogo);
    	   category.setBlacklogoUrl(path);
       }
       if(whitelogo!=null)
       {
    	   String path=storageService.uploadFileOnAzure(whitelogo);
    	   category.setWhitelogoUrl(path);
       }
       if(image!=null)
       {
    	   String path=storageService.uploadFileOnAzure(image);
    	   category.setImageUrl(path);
       }
    	Category createdCategory = categoryService.createCategory(category);
        Map<String,Object> response = new HashMap<>();
        response.put("Category", createdCategory );

        response.put("status", true); 
        
         return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK); 
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>>  getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        Map<String,Object> response = new HashMap<>();
        response.put("Category", categories);

        response.put("status", true); 
        
         return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
         
    }
}
