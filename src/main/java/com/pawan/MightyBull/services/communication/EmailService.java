package com.pawan.MightyBull.services.communication;

import com.pawan.MightyBull.dto.BaseDto;
import com.pawan.MightyBull.dto.communication.EmailTemplateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Pawan Saini
 * Created on 17/01/25.
 */
@Slf4j
@Service
public abstract class EmailService implements ICommunicationService {

    @Value("${sender.email}")
    private String senderEmail;

    @Value("${sender.password}")
    private String senderPassword;

    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${smtp.port}")
    private String smtpPort;

    @Override
    public void send(BaseDto baseDto) {
        EmailTemplateDto emailTemplateDTO = getEmailTemplateDTO(baseDto);
        Boolean isEmailSent = sendAlertEmail(emailTemplateDTO);
        log.info("Communication email request has been sent with : {}, {}, {}", isEmailSent, emailTemplateDTO.getTo(), emailTemplateDTO.getSubject());
    }

    protected abstract EmailTemplateDto getEmailTemplateDTO(BaseDto baseDto);

    private Boolean sendAlertEmail(EmailTemplateDto emailTemplateDTO) {
        try {
            // Email subject and body
            String subject = emailTemplateDTO.getSubject();
            String body = emailTemplateDTO.getTemplate();
            String recipientEmail = StringUtils.join(emailTemplateDTO.getTo(), ",");

            // Set properties for the mail session
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", smtpHost);
            properties.put("mail.smtp.port", smtpPort);

            // Create a session with the sender's credentials
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            // Send the email
            Transport.send(message);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while sending alert email for : {}, {}", emailTemplateDTO.getTo(), emailTemplateDTO.getSubject());
        }
        return false;
    }
}
