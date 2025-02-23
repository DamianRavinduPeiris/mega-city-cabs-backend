package com.damian.megacity.service.impl;

import com.damian.megacity.dto.VehicleDTO;
import com.damian.megacity.repo.DAOService;
import com.damian.megacity.service.VehicleService;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class VehicleServiceImpl implements VehicleService {
    private final DAOService<VehicleDTO> vehicleRepo;

    public VehicleServiceImpl(DAOService<VehicleDTO> vehicleRepo) {
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
}
