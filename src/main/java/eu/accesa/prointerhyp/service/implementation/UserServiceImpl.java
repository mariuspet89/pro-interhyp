package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class UserServiceImpl implements UserService {


   private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto findById(Long id) {

        LOGGER.info("Searching for the User with the following ID: " + id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow();

        return modelMapper.map(userEntity, UserDto.class);
    }
}
