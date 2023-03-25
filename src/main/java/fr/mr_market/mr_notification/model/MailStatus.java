package fr.mr_market.mr_notification.model;

public enum MailStatus {

    PENDING("PENDING"),

    SENT("SENT"),

    FAILED("FAILED");

    private String value;

    MailStatus(String value) {
        this.value = value;
    }
}
