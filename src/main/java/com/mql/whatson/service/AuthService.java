package com.mql.whatson.service;


import com.mql.whatson.entity.RegisterationRequest;
import com.mql.whatson.entity.User;

/**
 * @author Mehdi
 **/
public interface AuthService {

    /**
     * checks if the given mail exists in the database
     */
    User userHaveAccount(String email);

    /**
     * authenticate the given user object (checks email and password)
     */
    User authenticateUser(String email, String password);

    /**
     * register the given user object (if it does not already have an account)
     */
    boolean registerUser(RegisterationRequest request);


    /**
     * registers a user by google credentials
     */
    User registerUserGoogle(RegisterationRequest registrationRequest);

    User registerUserFacebook(RegisterationRequest registrationRequest);
}
