package fr.mr_market.mr_notification.model;

public enum MailType {

    ACCOUNT_ACTIVATION("account_activation"),

    ACCOUNT_CREATED("account_created");

    private final String value;

    MailType(String value) {
        this.value = value;
    }
}
