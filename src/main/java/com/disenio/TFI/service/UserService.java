package com.disenio.TFI.service;

import com.disenio.TFI.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String createUser(User user);
}