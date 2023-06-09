package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.exeptions.EntityNotFoundException;
import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.DepartmentService;
import eu.accesa.prointerhyp.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DepartmentService departmentService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.departmentService = departmentService;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        LOGGER.info("Creating User ");

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setId(UUID.randomUUID());
        UserEntity userEntitySaved = userRepository.save(userEntity);

        return modelMapper.map(userEntitySaved, UserDto.class);
    }

    @Override
    public UserDto findById(UUID id) {
        LOGGER.info("Searching for the User with the following ID: " + id);

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id: " + id + " not found."));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        LOGGER.info("Updating User " + userDto.getId());

        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("User with id: " + userDto.getId() + " not found."));
        modelMapper.map(userDto, userEntity);
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public void deleteUser(UUID id, String company) {
        LOGGER.info("Deleting the User with the following ID: " + id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id: " + id + " not found."));
        userRepository.deleteByIdAndCompany(id, company);
        DepartmentDto departmentDto=departmentService.findDepartmentByUserIdContaining(id);
        if(departmentDto!=null){
        UserToDepartmentDto department = new UserToDepartmentDto();
        department.setDepartment(departmentDto.getName());
        department.setUserId(id);
        departmentService.deleteUserFromDepartment(department);}
    }
}
