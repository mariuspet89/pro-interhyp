package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.UserDto;

public interface UserService {

    UserDto findById(Long id);

}
