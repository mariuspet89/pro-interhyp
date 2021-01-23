package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<UserDto> getAllUsersInDepartment(String department);

    DepartmentDto addUserToDepartment(UserToDepartmentDto dto);

    void deleteUserFromDepartment(UserToDepartmentDto dto);
}

