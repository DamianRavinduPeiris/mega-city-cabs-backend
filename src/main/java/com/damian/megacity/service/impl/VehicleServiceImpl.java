package com.damian.megacity.service.impl;

import com.damian.megacity.dto.VehicleDTO;
import com.damian.megacity.repo.DAOService;
import com.damian.megacity.repo.VehicleDAOService;
import com.damian.megacity.service.VehicleService;
import jakarta.servlet.http.Part;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class VehicleServiceImpl implements VehicleService {
    private final DAOService<VehicleDTO> vehicleRepo;
    private final VehicleDAOService vehicleDAOService;

    public VehicleServiceImpl(DAOService<VehicleDTO> vehicleRepo, VehicleDAOService vehicleDAOService) {
        this.vehicleDAOService = vehicleDAOService;
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public VehicleDTO add(VehicleDTO vehicleDTO) {
        return vehicleRepo.add(vehicleDTO);
    }

    @Override
    public VehicleDTO update(VehicleDTO vehicleDTO) {
        return vehicleRepo.update(vehicleDTO);
    }

    @Override
    public void delete(String id) {
        vehicleRepo.delete(id);
    }

    @Override
    public VehicleDTO search(String id) {
        return vehicleRepo.search(id);
    }

    @Override
    public List<VehicleDTO> getAll() {
        return vehicleRepo.getAll();
    }

    @Override
    public VehicleDTO addVehicleWithImages(VehicleDTO vehicleDTO, Part imageData) {
        return vehicleDAOService.addVehicleWithImages(vehicleDTO, imageData);
    }
}
