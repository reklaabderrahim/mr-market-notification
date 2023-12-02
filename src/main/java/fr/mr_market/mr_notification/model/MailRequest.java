package fr.mr_market.mr_notification.model;

import java.util.Set;
import java.util.StringJoiner;

public record MailRequest(Set<String> recipients, MailType mailType, String callbackUrl) {
    @Override
    public String toString() {
        return new StringJoiner(", ", MailRequest.class.getSimpleName() + "[", "]").add("mailType=" + mailType).add("callbackUrl=" + callbackUrl).toString();
    }
}
