package com.damian.megacity.service.constants;

public class EmailConstants {
    public static final String MAIl_SMTP_AUTH = "mail.smtp.auth";
    public static final String TRUE = "true";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String GMAIL_SMTP_HOST = System.getenv("GMAIL_SMTP_HOST");
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String GMAIL_SMTP_PORT = System.getenv("MAIL_SMTP_PORT");
    public static final String EMAIL_SENDING_FAILED = "Email sending failed!";
    public static final String BOOKING_SUBJECT = "Booking Confirmation.";
    public static final String FROM_EMAIL = System.getenv("FROM_EMAIL");
    public static final String APP_PASSWORD = System.getenv("APP_PASSWORD");

    private EmailConstants() {
    }
}
