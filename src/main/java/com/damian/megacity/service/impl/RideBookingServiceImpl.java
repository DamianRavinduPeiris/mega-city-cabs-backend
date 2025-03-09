package com.damian.megacity.service.impl;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.repo.RideBookingRepo;
import com.damian.megacity.repo.service.RideBookingDAOService;
import com.damian.megacity.service.EmailService;
import com.damian.megacity.service.RideBookingService;
import com.damian.megacity.util.user.UserUtil;

import java.util.List;

public class RideBookingServiceImpl implements RideBookingService<RideBookingDTO> {
    private final RideBookingDAOService<RideBookingDTO> rideBookingDAOService = new RideBookingRepo();
    private final EmailService<RideBookingDTO> emailService = new EmailServiceImpl();
    private final UserUtil userUtil = new UserUtil();

    @Override
    public RideBookingDTO add(RideBookingDTO rideBookingDTO) {
        var rbDTO = rideBookingDAOService.add(rideBookingDTO);
        emailService.sendEmail(rbDTO, userUtil.getUserEmail(rbDTO.userId()));
        return rbDTO;
    }

    @Override
    public List<RideBookingDTO> getAll() {
        return rideBookingDAOService.getAll();
    }
}
