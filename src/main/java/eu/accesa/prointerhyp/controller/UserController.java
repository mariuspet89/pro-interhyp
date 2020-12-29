package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        Iterable<UserEntity> result = userRepository.findAll();
        List<UserEntity> users = new ArrayList<UserEntity>();
        result.forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
