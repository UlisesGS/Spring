package com.envio.email.services;

import com.envio.email.services.models.EmailDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {

    public void sendMail(EmailDTO email) throws MessagingException;
}
