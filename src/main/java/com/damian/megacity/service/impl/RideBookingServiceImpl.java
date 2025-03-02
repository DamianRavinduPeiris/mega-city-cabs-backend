package com.damian.megacity.service.impl;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.repo.RideBookingRepo;
import com.damian.megacity.repo.service.RideBookingDAOService;
import com.damian.megacity.service.RideBookingService;

import java.util.List;

public class RideBookingServiceImpl implements RideBookingService<RideBookingDTO> {
    private final RideBookingDAOService<RideBookingDTO> rideBookingDAOService = new RideBookingRepo();

    @Override
    public RideBookingDTO add(RideBookingDTO rideBookingDTO) {
        return rideBookingDAOService.add(rideBookingDTO);
    }

    @Override
    public List<RideBookingDTO> getAll() {
        return rideBookingDAOService.getAll();
    }
}
