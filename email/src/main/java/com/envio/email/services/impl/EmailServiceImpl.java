package com.envio.email.services.impl;

import com.envio.email.services.IEmailService;
import com.envio.email.services.models.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendMail(EmailDTO email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email.getDestinatario());
            helper.setSubject(email.getAsunto());

            //Procesar plantilla de email.html
            Context context = new Context();
            context.setVariable("message", email.getMensaje());
            String contentHTML = templateEngine.process("email", context);//nombre de plantilla

            helper.setText(contentHTML, true);
            javaMailSender.send(message);

        } catch (Exception e){
            throw new RuntimeException("Error "+
                    "al enviar el correo: "+
                    e.getMessage(), e);
        }

    }
}
