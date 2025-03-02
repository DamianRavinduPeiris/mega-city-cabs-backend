package com.damian.megacity.repo.service;

import com.damian.megacity.dto.AdminDTO;

public interface AdminDAOService {
    AdminDTO searchByUserName(String userName);
}
