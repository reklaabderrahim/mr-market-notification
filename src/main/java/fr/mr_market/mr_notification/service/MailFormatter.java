package fr.mr_market.mr_notification.service;

import fr.mr_market.mr_notification.model.MailType;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Properties;

@Service
public class MailFormatter {
    private final VelocityContext velocityContext;
    private final VelocityEngine velocityEngine;

    private MailFormatter() {
        this.velocityContext = new VelocityContext();
        final Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loader", "file, class, jar");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        this.velocityEngine = new VelocityEngine(properties);
    }

    public String createContent(MailType mailType) throws IllegalArgumentException {
        completePlaceHolders(mailType);
        final Template templateEngine = velocityEngine.getTemplate("templates/mail-template.html");
        final StringWriter stringWriter = new StringWriter();
        templateEngine.merge(this.velocityContext, stringWriter);
        return stringWriter.toString();
    }

    public String getSubject(MailType mailType) {
        switch (mailType) {
            case ACCOUNT_ACTIVATION -> { return "J'active mon compte";}
            case ACCOUNT_CREATED -> { return "Bienvenue Parmi nous";}
            default -> { return "default";}
        }
    }

    private void completePlaceHolders(MailType mailType) {
        switch (mailType) {
            case ACCOUNT_ACTIVATION -> {
                velocityContext.put("subject", "Bonjour, Merci pour votre inscription");
                velocityContext.put("content", "Vous devez cliquer sur le lien ci-dessous pour valider votre inscription.");
                velocityContext.put("action_link", "http://localhost:4200");
                velocityContext.put("action_label", "JE CONFIRME");
            }
            case ACCOUNT_CREATED -> {
                velocityContext.put("subject", "Bonjour, Merci pour votre confiance");
                velocityContext.put("content", "Votre inscription est complete");
                velocityContext.put("action_link", "http://localhost:4200");
                velocityContext.put("action_label", "JE COMMENCE");
            }
            default -> {}
        }
    }
}
