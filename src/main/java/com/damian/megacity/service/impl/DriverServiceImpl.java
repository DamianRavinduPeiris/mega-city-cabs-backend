package com.damian.megacity.service.impl;

import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.repo.DAOService;
import com.damian.megacity.repo.DriverRepo;
import com.damian.megacity.service.DriverService;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    private final DAOService<DriverDTO> driverRepo = new DriverRepo();

    @Override
    public DriverDTO add(DriverDTO driverDTO) {
        return driverRepo.add(driverDTO);
    }

    @Override
    public DriverDTO update(DriverDTO driverDTO) {
        return driverRepo.update(driverDTO);
    }

    @Override
    public void delete(String id) {
        driverRepo.delete(id);
    }

    @Override
    public DriverDTO search(String id) {
        return driverRepo.search(id);
    }

    @Override
    public List<DriverDTO> getAll() {
        return driverRepo.getAll();
    }
}
