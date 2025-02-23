package com.damian.megacity.repo;

import com.damian.megacity.dto.VehicleDTO;
import jakarta.servlet.http.Part;

public interface VehicleDAOService extends DAOService<VehicleDTO> {
    void addImageDetails(Part filePart);
}
