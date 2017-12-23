package com.mql.whatson.service.producers;

import com.mql.whatson.service.qualifiers.UserProperties;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mehdi Maick
 */
@Dependent
public class PropertiesProducer {

    @Produces
    @UserProperties
    public Properties getMailProperties(InjectionPoint ip) {
        String fileName = ip.getAnnotated().getAnnotation(UserProperties.class).value();
        return loadProperties(fileName);
    }

    private Properties loadProperties(String fileInClasspath) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileInClasspath);
        try {
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
        }
        return null;
    }
}
