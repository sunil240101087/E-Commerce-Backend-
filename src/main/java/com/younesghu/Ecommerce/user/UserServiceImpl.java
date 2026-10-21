package com.younesghu.Ecommerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
