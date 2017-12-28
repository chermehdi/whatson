package com.mql.whatson.control.processor;

import com.mql.whatson.service.producers.TheymleafProducer;
import com.mql.whatson.service.qualifiers.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mehdi Maick
 */
public class TemplateProcessor {

    @Inject
    TheymleafProducer producer;

    Logger log = LoggerFactory.getLogger(getClass());

    public void process(HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext context,
                        String templateName) {
        WebContext ctx = new WebContext(request, response, context, request.getLocale());
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1000);
        TemplateEngine engine = producer.getTemplateEngine(context);
        try {
            engine.process(templateName, ctx, response.getWriter());
        } catch (IOException e) {
            log.info("cannot resolve template name");
            e.printStackTrace();
        }
    }
}
