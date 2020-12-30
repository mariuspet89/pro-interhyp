package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository;
    UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        Iterable<UserEntity> result = userRepository.findAll();
        List<UserEntity> users = new ArrayList<UserEntity>();
        result.forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));

    }
}
