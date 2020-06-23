package org.renuka.learn.java.springboot.starter.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi Everyone";
	}
	
	@RequestMapping("/bye")
	public String bye() {
		return "Take Care. Enjoy your day...";
	}
	
	@RequestMapping("/errorPage")
	public String errorPage() {
		return "Missing page. Sorry for the same..";
	}
}
