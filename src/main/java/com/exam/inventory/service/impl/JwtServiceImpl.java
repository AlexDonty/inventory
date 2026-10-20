package com.exam.inventory.service.impl;

import com.exam.inventory.dtos.LoginDto;
import com.exam.inventory.dtos.TokenDto;
import com.exam.inventory.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    @Value("${app.jwt.expiration-minutes}")
    public long expirationMinutes;
    private final JwtEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDto generateToken(LoginDto request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        Instant now = Instant.now();

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.startsWith("ROLE_"))
                .map(role -> role.substring(5))
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("inventory-api")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(
                        expirationMinutes, ChronoUnit.MINUTES))
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        String token = encoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();

        return new TokenDto(token, roles.isEmpty() ? null : roles.get(0));
    }
}
