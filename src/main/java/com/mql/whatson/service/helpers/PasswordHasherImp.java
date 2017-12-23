package com.mql.whatson.service.helpers;

import com.mql.whatson.service.qualifiers.UserProperties;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * @author Mehdi Maick
 */
public class PasswordHasherImp implements PasswordHasher {

    @Inject
    @UserProperties("passwords.properties")
    public Properties properties;

    @Override
    public String getHash(String password) {
        StringBuilder hash = new StringBuilder();
        password = properties.getProperty("whatson.password.salt") + password;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(password.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            for (byte b : hashedBytes) {
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hash.toString();
    }
}
