package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.model.dto.SortingAndFilteringDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    UserRepository userRepository;
    UserService userService;
    ModelMapper modelMapper;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@Valid @RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/birthday")
    public ResponseEntity<Slice<UserDto>> getAllUsersByBirthday(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = modelMapper.map(userService.filteredFindAll(sortingAndFilteringDto),
                new TypeToken<Slice<UserDto>>() {
        }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }
    @GetMapping("/first-name")
    public ResponseEntity<Slice<UserDto>> getAllUsersByFirstName(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = modelMapper.map(userService.filteredFindAll(sortingAndFilteringDto),
                new TypeToken<Slice<UserDto>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }
    @GetMapping("/last-name")
    public ResponseEntity<Slice<UserDto>> getAllUsersByLastName(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = modelMapper.map(userService.filteredFindAll(sortingAndFilteringDto),
                new TypeToken<Slice<UserDto>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }
    @GetMapping("/details")
    public ResponseEntity<Slice<UserDto>> getAllUsersByDetails(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = modelMapper.map(userService.filteredFindAll(sortingAndFilteringDto),
                new TypeToken<Slice<UserDto>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }
    @GetMapping("/username")
    public ResponseEntity<Slice<UserDto>> getAllUsersByUsername(@RequestBody SortingAndFilteringDto sortingAndFilteringDto) {
        Slice<UserDto> userDtos = modelMapper.map(userService.filteredFindAll(sortingAndFilteringDto),
                new TypeToken<Slice<UserDto>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto)  {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));
    }


}
