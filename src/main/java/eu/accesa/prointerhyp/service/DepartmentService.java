package eu.accesa.prointerhyp.service;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    List<DepartmentDto> addUserToDepartment(String department, UUID userId);
}
