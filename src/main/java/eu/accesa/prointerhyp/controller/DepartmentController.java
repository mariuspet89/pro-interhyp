package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.DepartmentWithUsersDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;
import eu.accesa.prointerhyp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentWithUsersDto> addUserToDepartment(@RequestBody UserToDepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addUserToDepartment(dto));
    }

    @PostMapping("/add-department")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addDepartment(departmentDto));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentWithUsersDto>> getAllDepartments(){
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllDepartments());
    }

    @GetMapping("/{users}")
    public ResponseEntity<DepartmentWithUsersDto> getAllUsersInDepartment(@PathVariable(value = "users") String department) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllUsersInDepartment(department));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserFromDepartment(@RequestBody UserToDepartmentDto dto) {
        departmentService.deleteUserFromDepartment(dto);
        return ResponseEntity.status(HttpStatus.OK).body("User has been deleted.");
    }

    @DeleteMapping("/delete-department/{department}")
    public ResponseEntity<String> deleteDepartment(@PathVariable(value = "department") String departmentName) {
        departmentService.deleteDepartment(departmentName);
        return ResponseEntity.status(HttpStatus.OK).body("Department has been deleted.");
    }
}
