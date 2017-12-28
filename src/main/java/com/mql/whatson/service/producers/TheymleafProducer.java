package com.mql.whatson.service.producers;

import com.mql.whatson.service.qualifiers.Engine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;

/**
 * produces a template engine for thymeleaf
 *
 * @author Mehdi Maick
 */
public class TheymleafProducer {

    public TemplateEngine getTemplateEngine(ServletContext context) {
        ServletContextTemplateResolver contextTemplateResolver = new ServletContextTemplateResolver(context);
        contextTemplateResolver.setTemplateMode(TemplateMode.HTML);
        contextTemplateResolver.setPrefix("/WEB-INF/pages/");
        contextTemplateResolver.setSuffix(".html");
        contextTemplateResolver.setCacheTTLMs(3600000L);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(contextTemplateResolver);
        return templateEngine;
    }
}
