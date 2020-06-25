package org.renuka.learn.java.security.dbjpamysql;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome to our app developed using spring security, JPA authentication with MySQL</h1>");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1>Welcome to REGISTERED USER SECTION of our app developed using spring security, " +
                "JPA authentication with MySQL</h1>");
    }
    @GetMapping("/admin")
    public String admin(){
        return ("<h1>Welcome to REGISTERED ADMIN SECTION of our app developed using spring security, " +
                "JPA authentication with MySQL</h1>");
    }
}
