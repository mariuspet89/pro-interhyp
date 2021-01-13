package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.SortingAndFilteringDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.repository.*;
import eu.accesa.prointerhyp.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserByDetailsRepository userByDetailsRepository;
    private final UserByBirthdayRepository userByBirthdayRepository;
    private final UserByFirstNameRepository userByFirstNameRepository;
    private final UserByLastNameRepository userByLastNameRepository;
    private final UserByUserNameRepository userByUserNameRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserByDetailsRepository userByDetailsRepository,
                           UserByBirthdayRepository userByBirthdayRepository,
                           UserByFirstNameRepository userByFirstNameRepository,
                           UserByLastNameRepository userByLastNameRepository,
                           UserByUserNameRepository userByUserNameRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userByDetailsRepository = userByDetailsRepository;
        this.userByBirthdayRepository = userByBirthdayRepository;
        this.userByFirstNameRepository = userByFirstNameRepository;
        this.userByLastNameRepository = userByLastNameRepository;
        this.userByUserNameRepository = userByUserNameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        LOGGER.info("Creating User ");

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setId(UUID.randomUUID());
        UserEntity userEntitySaved = userRepository.save(userEntity);
        UserDto savedDto = modelMapper.map(userEntitySaved, UserDto.class);

        return savedDto;
    }

    @Override
    public UserDto findById(UUID id) {

        LOGGER.info("Searching for the User with the following ID: " + id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow();

        return modelMapper.map(userEntity, UserDto.class);
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
    public Slice<UserDto> filteredFindAll(SortingAndFilteringDto sortingAndFilteringDto) {
        String filterKeyword = sortingAndFilteringDto.getFilterKeyword();

        CassandraPageRequest request = CassandraPageRequest.of(0, sortingAndFilteringDto.getItemsPerPage(),
                Sort.Direction.fromString(sortingAndFilteringDto.getOrderDirection()));

        Slice<UserDto> slice;

        switch (filterKeyword) {
            case "birthday":
                slice = modelMapper.map(userByBirthdayRepository.findAll(request),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
            case "details":
                slice = modelMapper.map(userByDetailsRepository.findAll(request),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
            case "first_name":
                slice = modelMapper.map(userByFirstNameRepository.findAll(request),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
            case "last_name":
                slice = modelMapper.map(userByFirstNameRepository.findAll(request),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
            case "username":
                slice = modelMapper.map(userByUserNameRepository.findAll(request),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
            default:slice = modelMapper.map(userRepository.findAll(request),
                    new TypeToken<Slice<UserDto>>() {}.getType());
        }


        return slice;
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(toList());
    }

    @Override
    public void deleteUser(UUID id) {
        LOGGER.info("Deleting the User with the following ID: " + id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        userRepository.delete(userEntity);
    }
}
