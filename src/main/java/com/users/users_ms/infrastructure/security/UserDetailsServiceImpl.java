package com.users.users_ms.infrastructure.security;

import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.domain.model.User;


import com.users.users_ms.domain.ports.out.UserPersistencePort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPersistencePort UserPersistencePort;

    public UserDetailsServiceImpl(UserPersistencePort UserPersistencePort) {
        this.UserPersistencePort = UserPersistencePort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = UserPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
