package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.User;
import com.disenio.TFI.repository.UserRepository;
import com.disenio.TFI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public String createUser(User user) {
        userRepository.save(user);
        return "El usuario se guardó con éxito";
    }
}