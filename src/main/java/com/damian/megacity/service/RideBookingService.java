package com.damian.megacity.service;


import java.util.List;

public interface RideBookingService<RideBookingDTO> {
    RideBookingDTO add(RideBookingDTO t);

    List<RideBookingDTO> getAll();
}
