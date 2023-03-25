package fr.mr_market.mr_notification.service;


import fr.mr_market.mr_notification.model.Mail;
import fr.mr_market.mr_notification.model.MailType;
import jakarta.mail.MessagingException;

import java.util.Set;

public interface MailService
{
	void sendHTMLEmail(Mail message) throws MessagingException;
	Mail create(Set<String> recipients, MailType mailType);
}
