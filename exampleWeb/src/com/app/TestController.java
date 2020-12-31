package com.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/restcoffees")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@GetMapping
    public List getList(){
		System.out.println("restcoffees................");
    	return null;
    }
}