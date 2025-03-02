package com.damian.megacity.repo.service;

import java.util.List;

public interface RideBookingDAOService<RideBookingDTO> {
    RideBookingDTO add(RideBookingDTO t);

    List<RideBookingDTO> getAll();
}
