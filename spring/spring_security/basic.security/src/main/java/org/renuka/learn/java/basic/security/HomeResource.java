package org.renuka.learn.java.basic.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/")
    public String home(){
        return ("<h1> Welcome to my app </h1>");
    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h1> Welcome admin to admin section of my app </h1>");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1> Welcome registered user to the registered user section of my app </h1>");
    }
}
