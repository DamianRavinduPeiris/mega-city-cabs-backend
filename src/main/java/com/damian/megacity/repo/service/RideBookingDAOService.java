package com.damian.megacity.repo.service;

import com.damian.megacity.dto.RideBookingDTO;

import java.util.List;

public interface RideBookingDAOService {
    RideBookingDTO add(RideBookingDTO t);

    List<RideBookingDTO> getAll();
}
