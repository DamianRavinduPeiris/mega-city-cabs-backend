package com.damian.megacity.service;

import com.damian.megacity.dto.RideBookingDTO;

public interface EmailService {
    void sendEmail(RideBookingDTO rideBookingDTO, String toEmail);
}
