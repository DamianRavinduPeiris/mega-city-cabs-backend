package com.damian.megacity.util.user;

import com.damian.megacity.repo.UserRepo;
import com.damian.megacity.repo.service.UserDAOService;

public class UserUtil {
    private final UserDAOService userRepo = new UserRepo();

    public String getUserEmail(String userId) {
        var user = userRepo.search(userId);
        return user != null ? user.email() : null;
    }
}
