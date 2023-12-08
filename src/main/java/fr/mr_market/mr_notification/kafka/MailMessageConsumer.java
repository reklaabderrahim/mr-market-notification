package fr.mr_market.mr_notification.kafka;

import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailRequest;
import fr.mr_market.mr_notification.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailMessageConsumer {

    private final MailService mailService;

    @KafkaListener(topics = "mail-register")
    public void listen(MailRequest request) throws MessagingException {
        log.debug("Mail message received: {}", request);
        final Mail mail = mailService.create(request.recipients(), request.mailType(), request.callbackUrl());
        mailService.sendHTMLEmail(mail);
    }

}
