package fr.mr_market.mr_notification.service;

import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailStatus;
import fr.mr_market.mr_notification.model.MailType;
import fr.mr_market.mr_notification.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MailFormatter mailFormatter;
    private final MailRepository mailRepository;
    private final Environment environment;

    @Override
    @Async
    @Transactional
    public void sendHTMLEmail(Mail message) throws MessagingException {
        MimeMessage emailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
        mailBuilder.setTo(message.getRecipients().toArray(String[]::new));
        mailBuilder.setFrom(message.getFrom());
        mailBuilder.setText(message.getBody(), true);
        mailBuilder.setSubject(message.getSubject());
        javaMailSender.send(emailMessage);
        mailRepository.updateStatus(message.getId(), MailStatus.SENT, LocalDateTime.now());
    }

    @Override
    public Mail create(Set<String> recipients, MailType mailType, String callbackUrl) {
        Mail mail = Mail.create(
                environment.getProperty("spring.mail.username"),
                recipients,
                mailFormatter.getSubject(mailType),
                mailFormatter.createContent(mailType, callbackUrl),
                "mail-template",
                mailType,
                callbackUrl
        );
        return mailRepository.save(mail);
    }
}
