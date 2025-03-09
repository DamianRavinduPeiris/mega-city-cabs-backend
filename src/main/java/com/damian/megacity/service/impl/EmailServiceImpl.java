package com.damian.megacity.service.impl;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.exceptions.EmailException;
import com.damian.megacity.service.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.damian.megacity.service.constants.EmailConstants.*;

public class EmailServiceImpl implements EmailService<RideBookingDTO> {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);


    @Override
    public void sendEmail(RideBookingDTO rideBookingDTO, String toEmail) {
        executor.submit(() -> {
            var props = new Properties();
            props.put(MAIl_SMTP_AUTH, TRUE);
            props.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
            props.put(MAIL_SMTP_HOST, GMAIL_SMTP_HOST);
            props.put(MAIL_SMTP_PORT, GMAIL_SMTP_PORT);

            var session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });


            try {
                var message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(BOOKING_SUBJECT);

                var emailBody = String.format(
                        """
                                Dear %s,
                                
                                Your booking has been confirmed. Thank you for choosing MegaCity Cabs.
                                
                                Ride Details:
                                - Order ID: %s
                                - Driver ID: %s
                                - Vehicle: %s (%s)
                                - From: %s
                                - To: %s
                                - Date: %s
                                - Duration: %s
                                - Price: %.2f
                                
                                
                                
                                Best Regards,
                                MegaCity Cabs Team.
                                """,
                        rideBookingDTO.userName(),
                        rideBookingDTO.orderId(),
                        rideBookingDTO.driverId(),
                        rideBookingDTO.vehicleName(),
                        rideBookingDTO.vehicleNumberPlate(),
                        rideBookingDTO.pickUpCity(),
                        rideBookingDTO.destinationCity(),
                        rideBookingDTO.date(),
                        rideBookingDTO.duration(),
                        rideBookingDTO.price()
                );

                message.setText(emailBody);

                Transport.send(message);
            } catch (MessagingException e) {
                throw new EmailException(EMAIL_SENDING_FAILED);
            }
        });
    }
}
