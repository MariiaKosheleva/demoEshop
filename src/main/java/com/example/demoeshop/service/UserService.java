package com.example.demoeshop.service;

import com.example.demoeshop.dto.UserDto;
import com.example.demoeshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);

    void save(User user);

    List<UserDto> getAll();

    User findByName(String name);

    void update(UserDto userDto);

    void delete(Long userId);
}
