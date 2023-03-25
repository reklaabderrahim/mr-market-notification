package fr.mr_market.mr_notification.service;

import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailType;
import fr.mr_market.mr_notification.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MailFormatter mailFormatter;
    private final MailRepository mailRepository;

/*    @Value("spring.mail.username")
    private final String username;*/

    @Override
    public void sendHTMLEmail(Mail message) throws MessagingException {
        MimeMessage emailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
        mailBuilder.setTo(message.getRecipients().toArray(String[]::new));
        mailBuilder.setFrom(message.getFrom());
        mailBuilder.setText(message.getBody(), true);
        mailBuilder.setSubject(message.getSubject());
        javaMailSender.send(emailMessage);
    }

    @Override
    public Mail create(Set<String> recipients, MailType mailType) {
        Mail mail = Mail.create(
                "reklaabderrahim@gmail.com",
                recipients,
                mailFormatter.getSubject(mailType),
                mailFormatter.createContent(mailType),
                "mail-template",
                mailType
        );
        return mailRepository.save(mail);
    }
}
