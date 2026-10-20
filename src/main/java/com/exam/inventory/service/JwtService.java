package com.exam.inventory.service;

import com.exam.inventory.dtos.LoginDto;
import com.exam.inventory.dtos.TokenDto;
import org.springframework.security.core.Authentication;

public interface JwtService {

    public TokenDto generateToken(LoginDto request);
}
