package com.disenio.TFI.service;

import com.disenio.TFI.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(User user);
}