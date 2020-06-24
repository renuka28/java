package org.renuka.learn.java.basic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //only for demo. this is poor way of doing security. don't embed any password in cleartext
        //and push to public git repo. Better yet, dont embed any password in cleartext
        auth.inMemoryAuthentication()
                .withUser("renuka")
                .password("renuka")
                .roles("ADMIN").and()
                .withUser("gubol")
                .password("gubol")
                .roles("Non Admin");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
