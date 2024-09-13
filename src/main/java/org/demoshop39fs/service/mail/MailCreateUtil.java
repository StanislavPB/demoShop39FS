package org.demoshop39fs.service.mail;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailCreateUtil {

    private final Configuration freemarkerConfiguration;

    public String createConfirmationMail(String firstName, String lastName, String link){

        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_registration_mail.ftlh");
            Map<Object,Object> model = new HashMap<>();
            model.put("firstName", firstName);
            model.put("lastName", lastName);
            model.put("link", link);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        } catch (Exception e) {
            throw new IllegalStateException("Error while processing email template", e);
        }

    }

}
