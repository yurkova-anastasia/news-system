package ru.clevertec.user_service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.user_service.controller.request.SignUpRequest;
import ru.clevertec.user_service.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.FIELD)
public interface UserMapper {

    /**
     * Maps a {@link SignUpRequest} to a {@link User} entity.
     *
     * @param signUpRequest the user sign up request
     * @return the user entity
     */
    User toEntity(SignUpRequest signUpRequest);

}
