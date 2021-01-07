package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.UserDto;

import java.util.UUID;

public interface UserService {

    UserDto findById(UUID id);

    void deleteUser(UUID id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

}
