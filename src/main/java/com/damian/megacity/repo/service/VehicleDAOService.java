package com.damian.megacity.repo.service;

import com.damian.megacity.dto.VehicleDTO;
import jakarta.servlet.http.Part;

public interface VehicleDAOService extends DAOService<VehicleDTO> {
    VehicleDTO addVehicleWithImages(VehicleDTO vehicleDTO,Part imageData);
}
