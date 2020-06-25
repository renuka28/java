package org.renuka.learn.java.spring.security.ldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
//
//    @GetMapping("/")
//    public String home(){
//        return ("<h1>Welcome to our app which used Spring security with credentials" +
//                " stored in a LDAP Directory </h1>");
//    }
//
//    @GetMapping("/user")
//    public String user(){
//        return ("<h1>Welcome to user second of the app developed using Spring security with " +
//                "credentials stored in a LDAP Directory</h1>");
//    }
//
//    @GetMapping("/admin")
//    public String admin(){
//        return ("<h1>Welcome to ADMIN section of the app developed using  Spring security with " +
//                "credentials stored in a LDAP Directory</h1>");
//    }

    @GetMapping("/")
    public String index() {
        return "Welcome to the home page!";
    }
}
