package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.UserDto;
import org.springframework.data.cassandra.repository.AllowFiltering;

import java.util.List;
import java.util.UUID;

public interface UserService {
    @AllowFiltering
    UserDto findById(UUID id);

    void deleteUser(UUID id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    List<UserDto> findAll();

}
