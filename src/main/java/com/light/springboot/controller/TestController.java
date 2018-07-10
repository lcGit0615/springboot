package com.light.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.light.springboot.bean.User;

@RestController
@EnableConfigurationProperties({User.class})
public class TestController {
	
	@GetMapping("/helloworld")
	public String helloworld(){
		return "helloworld";
	}
	
	@Autowired
	User user;
	@RequestMapping(value="/user")
	public String user(){
		return user.getName()+user.getAge();
	}
	
}
