package com.cgi.accountservice.repository;

import com.cgi.accountservice.data.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * This repository used for User entity related functionality to authenticate user
 *
 * @author Rajesh Chanda
 * @version 0.1
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * It searches a existing user from table
     * @param userName - take a username to find the existing user from table users
     * @return it return a user entity which holds user information,if it present else optional object
     */
    Optional<User> findByUserName(String userName);

    /**
     * It delete a user from the table
     * @param userName - take a username to delete user from table users
     * @return
     */
    void deleteByUserName(String userName);


}
