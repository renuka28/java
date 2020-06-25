
package org.renuka.learn.java.security.dbjpamysql;

import org.renuka.learn.java.security.dbjpamysql.model.MyUserDetails;
import org.renuka.learn.java.security.dbjpamysql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("getting user info for user  - "+userName);
        //refer to data.sql for values inserted
        Optional<User> user =  userRepository.findByUserName(userName);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found : - " + userName));
        MyUserDetails userDetails = user.map(MyUserDetails::new).get();
        System.out.println(userDetails);
        return userDetails;
    }
}
