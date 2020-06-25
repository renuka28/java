package org.renuka.learn.java.security.dbjpamysql;

import org.renuka.learn.java.security.dbjpamysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//implemetnation automatically provided by JPA.
public interface UserRepository extends JpaRepository<User, Integer> {

    //name in a standard way findBy<fieldName>
    Optional<User> findByUserName(String userName);
}
