package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.dto.SortingAndFilteringDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.service.UserService;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/filtered-find-all")
    public ResponseEntity<Slice<UserDto>> filteredFindAll(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = userService.filteredFindAll(sortingAndFilteringDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/filtered-find-by")
    public ResponseEntity<List<UserDto>> filteredFindBy(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        List<UserDto> userDtos = userService.findAllByDetails(sortingAndFilteringDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));
    }

    @DeleteMapping("/{id}/{company}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id, @PathVariable("company") String company) {
        userService.deleteUser(id, company);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }
}
