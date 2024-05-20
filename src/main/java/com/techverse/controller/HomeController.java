package com.techverse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 

@RestController
@RequestMapping("")
public class HomeController {

	// @ApiOperation(value = "Get a greeting message", notes = "Returns a simple greeting message.")
	 @GetMapping("/")
	 public String hello() {
	  return "Hello, Swagger!";
	    }
}
