package com.masterdrive.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/status")
	public String getStatus() {
		return "Service is working fine";
	}
	
}
