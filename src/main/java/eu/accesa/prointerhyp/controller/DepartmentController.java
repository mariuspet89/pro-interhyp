package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;
import eu.accesa.prointerhyp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<List<DepartmentDto>> addUserToDepartment(@RequestBody UserToDepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.addUserToDepartment(dto.getDepartment(), dto.getUserId()));
    }

    @DeleteMapping
    public void deleteUserFromDto(@RequestBody UserToDepartmentDto dto) {
        departmentService.deleteUserFromDepartment(dto);
    }
}
