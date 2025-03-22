package com.damian.megacity.service.impl;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.exceptions.EmailException;
import com.damian.megacity.repo.RideBookingRepo;
import com.damian.megacity.repo.service.RideBookingDAOService;
import com.damian.megacity.service.EmailService;
import com.damian.megacity.service.RideBookingService;

import java.util.List;

import static com.damian.megacity.service.constants.EmailConstants.EMAIL_ADDRESS_NOT_FOUND;

public class RideBookingServiceImpl implements RideBookingService<RideBookingDTO> {
    private final RideBookingDAOService rideBookingDAOService = new RideBookingRepo();
    private final EmailService emailService = new EmailServiceImpl();

    @Override
    public RideBookingDTO add(RideBookingDTO rideBookingDTO, String userEmail) {
        if (userEmail != null) {
            var rbDTO = rideBookingDAOService.add(rideBookingDTO);
            emailService.sendEmail(rbDTO, userEmail);
            return rbDTO;
        }
        throw new EmailException(EMAIL_ADDRESS_NOT_FOUND);
    }

    @Override
    public List<RideBookingDTO> getAll() {
        return rideBookingDAOService.getAll();
    }
}
