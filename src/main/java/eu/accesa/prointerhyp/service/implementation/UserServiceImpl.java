package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.repository.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
        //TODO implement exception handling
        LOGGER.info("Searching for the User with the following ID: " + id);

        UserEntity userEntity = userRepository.findById(id).orElseThrow();
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

        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow();
        modelMapper.map(userDto, userEntity);
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public void deleteUser(UUID id, String company) {
        LOGGER.info("Deleting the User with the following ID: " + id);

        userRepository.deleteByIdAndCompany(id, company);
    }
}
