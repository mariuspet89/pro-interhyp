package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.DepartmentDtoForGet;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDtoForGet getAllUsersInDepartment(String department);

    DepartmentDtoForGet addUserToDepartment(UserToDepartmentDto dto);

    void deleteUserFromDepartment(UserToDepartmentDto dto);

    List<DepartmentDtoForGet> getAllDepartments();

    void deleteDepartment(String departmentName);

    DepartmentDto addDepartment (DepartmentDto departmentDto);

}

