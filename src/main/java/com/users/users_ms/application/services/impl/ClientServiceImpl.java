package com.users.users_ms.application.services.impl;

import com.users.users_ms.application.dto.request.CreateClientRequestDto;
import com.users.users_ms.application.mappers.UserDtoMapper;
import com.users.users_ms.application.services.ClientService;
import com.users.users_ms.commons.constants.ExceptionMessages;
import com.users.users_ms.commons.exceptions.NotFoundException;
import com.users.users_ms.domain.model.User;
import com.users.users_ms.domain.ports.in.RegisterClientServicePort;
import com.users.users_ms.domain.ports.out.UserPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ClientServiceImpl implements ClientService {

    private final RegisterClientServicePort registerClient;
    private final UserPersistencePort port;
    private final UserDtoMapper mapper;


    @Override
    public void saveClient(CreateClientRequestDto dto) {
        User client = mapper.toModel(dto);
        registerClient.saveClient(client);

    }

    @Override
    public String getPhoneByUserId(Long id) {
        return port.findById(id)
                .map(User::getPhone)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_ID));
    }
}
