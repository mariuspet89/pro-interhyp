package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
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
    public ResponseEntity<DepartmentDto> addUserToDepartment(@RequestBody UserToDepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addUserToDepartment(dto));
    }

    @GetMapping("/{users}")
    public ResponseEntity<List<UserDto>> getAllUsersInDepartment(@PathVariable(value = "users") String department) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllUsersInDepartment(department));
    }

    @DeleteMapping
    public void deleteUserFromDto(@RequestBody UserToDepartmentDto dto) {
        departmentService.deleteUserFromDepartment(dto);
    }
}
