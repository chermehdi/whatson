package com.mql.whatson.service.helpers;

/**
 * @author Mehdi Maick
 */
public interface PasswordHasher {

    String getHash(String password);
}
