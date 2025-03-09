package com.damian.megacity.service.impl;

import com.damian.megacity.exceptions.AdminException;
import com.damian.megacity.repo.service.AdminDAOService;
import com.damian.megacity.repo.AdminRepo;
import com.damian.megacity.service.AdminService;
import com.damian.megacity.util.encryption.EncryptionUtil;
import lombok.extern.java.Log;

@Log
public class AdminServiceImpl implements AdminService {
    private final AdminDAOService adminRepo = new AdminRepo();

    @Override
    public boolean searchByUsernameAndAuthenticate(String username, String password) {
        var admin = adminRepo.searchByUserName(username);
        if (admin != null) {
            try {
                return admin.password().equals(EncryptionUtil.encrypt(password));
            } catch (Exception e) {
                log.warning("An error occurred while authenticating the admin: " + e.getMessage());
                throw new AdminException("An error occurred while authenticating the admin.");
            }
        }
        return false;
    }
}
