package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.DepartmentWithUsersDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentWithUsersDto getAllUsersInDepartment(String department);

    DepartmentWithUsersDto addUserToDepartment(UserToDepartmentDto dto);

    void deleteUserFromDepartment(UserToDepartmentDto dto);

    List<DepartmentWithUsersDto> getAllDepartments();

    void deleteDepartment(String departmentName);

    DepartmentDto addDepartment (DepartmentDto departmentDto);

}

