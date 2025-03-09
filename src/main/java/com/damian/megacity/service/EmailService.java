package com.damian.megacity.service;

import com.damian.megacity.dto.RideBookingDTO;

public interface EmailService<T extends RideBookingDTO> {
    void sendEmail(T t,String toEmail);
}
