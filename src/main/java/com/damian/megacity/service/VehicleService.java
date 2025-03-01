package com.damian.megacity.service;

import com.damian.megacity.dto.VehicleDTO;
import jakarta.servlet.http.Part;

public interface VehicleService extends CabService<VehicleDTO> {
   VehicleDTO addVehicleWithImages(VehicleDTO vehicleDTO, Part imageData);
}
