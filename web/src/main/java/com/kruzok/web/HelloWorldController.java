package com.kruzok.web;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
	
	@Resource(name = "mongoTemplate")
	MongoOperations mongoOperation;

	private static final Logger logger = LogManager
			.getLogger(HelloWorldController.class);

	@RequestMapping("/")
	public String hello(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		
		model.addAttribute("name", name);
		return "index";
	}
	
	
	
}