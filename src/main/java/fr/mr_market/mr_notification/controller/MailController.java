package fr.mr_market.mr_notification.controller;

import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailRequest;
import fr.mr_market.mr_notification.model.MailResponse;
import fr.mr_market.mr_notification.model.MailStatus;
import fr.mr_market.mr_notification.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @PostMapping("/mailProcessing")
    public ResponseEntity<MailResponse> sendTestReport(@RequestBody MailRequest request) throws MessagingException {
        final Mail mail = mailService.create(request.recipients(), request.mailType(), request.callbackUrl());
        mailService.sendHTMLEmail(mail);
        return new ResponseEntity<>(new MailResponse("Your request was taken under consideration", MailStatus.PENDING), HttpStatus.OK);
    }
}
