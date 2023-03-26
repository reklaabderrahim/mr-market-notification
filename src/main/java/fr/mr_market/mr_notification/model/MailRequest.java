package fr.mr_market.mr_notification.model;

import java.util.Set;

public record MailRequest(Set<String> recipients, MailType mailType, String callbackUrl) {
}
