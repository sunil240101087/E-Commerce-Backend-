package com.younesghu.Ecommerce.auth;

import com.younesghu.Ecommerce.exception.EmailAlreadyExistsException;
import com.younesghu.Ecommerce.exception.UserNotFoundException;
import com.younesghu.Ecommerce.user.Role;
import com.younesghu.Ecommerce.user.SecurityUser;
import com.younesghu.Ecommerce.user.User;
import com.younesghu.Ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.CUSTOMER);

        userRepository.save(user);

        var securityUser = new SecurityUser(user);
        var jwtToken = jwtService.generateToken(securityUser);

        return new AuthResponse(jwtToken, null, "Bearer", 86400L);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        var securityUser = new SecurityUser(user);
        var jwtToken = jwtService.generateToken(securityUser);

        return new AuthResponse(jwtToken, null, "Bearer", 86400L);
    }
}
