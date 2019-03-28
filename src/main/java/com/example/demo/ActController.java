package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActController {
	
	@RequestMapping(value="/")
	public String home()
	{
		return "Hello Act";
	}

}
