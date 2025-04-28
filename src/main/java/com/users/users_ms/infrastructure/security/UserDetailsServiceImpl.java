package com.users.users_ms.infrastructure.security;

import com.users.users_ms.domain.model.User;


import com.users.users_ms.domain.ports.out.IUserPersistencePort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserPersistencePort IUserPersistencePort;

    public UserDetailsServiceImpl(IUserPersistencePort IUserPersistencePort) {
        this.IUserPersistencePort = IUserPersistencePort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = IUserPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
