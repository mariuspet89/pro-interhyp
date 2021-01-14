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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        return modelMapper.map(userEntitySaved, UserDto.class);
    }

    @Override
    public UserDto findById(UUID id) {
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
    public Slice<UserDto> filteredFindAll(SortingAndFilteringDto sortingAndFilteringDto) {
        String filterKeyword = sortingAndFilteringDto.getFilterKeyword();
        CassandraPageRequest request = CassandraPageRequest.of(0, sortingAndFilteringDto.getItemsPerPage());

        Slice<UserDto> slice;

        switch (filterKeyword) {
            case "birthday":
                return handleBirthday(sortingAndFilteringDto, request);
            case "details":
                return handleDetails(sortingAndFilteringDto, request);
            case "first_name":
                return handleFirstName(sortingAndFilteringDto, request);
            case "last_name":
                return handleLastName(sortingAndFilteringDto, request);
            case "username":
                return handleUserName(sortingAndFilteringDto, request);
            default:
                slice = modelMapper.map(userRepository.findAll(),
                        new TypeToken<Slice<UserDto>>() {
                        }.getType());
        }

        if (slice == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No entries found.");
        }
        return slice;
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

    private Slice<UserDto> handleBirthday(SortingAndFilteringDto sortingAndFilteringDto, CassandraPageRequest request) {
        if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("ASC")) {
            return modelMapper.map(userByBirthdayRepository.findAllBirthdayAscending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        } else if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("DESC")) {
            return modelMapper.map(userByBirthdayRepository.findAllBirthdayDescending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        }
        return null;
    }

    private Slice<UserDto> handleDetails(SortingAndFilteringDto sortingAndFilteringDto, CassandraPageRequest request) {
        if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("ASC")) {
            return modelMapper.map(userByDetailsRepository.findAllDetailsAscending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        } else if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("DESC")) {
            return modelMapper.map(userByDetailsRepository.findAllDetailsDescending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        }
        return null;
    }

    private Slice<UserDto> handleFirstName(SortingAndFilteringDto sortingAndFilteringDto, CassandraPageRequest request) {
        if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("ASC")) {
            return modelMapper.map(userByFirstNameRepository.findAllFirstNameAscending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        } else if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("DESC")) {
            return modelMapper.map(userByFirstNameRepository.findAllFirstNameDescending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        }
        return null;
    }

    private Slice<UserDto> handleLastName(SortingAndFilteringDto sortingAndFilteringDto, CassandraPageRequest request) {
        if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("ASC")) {
            return modelMapper.map(userByLastNameRepository.findAllLastNameAscending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        } else if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("DESC")) {
            return modelMapper.map(userByLastNameRepository.findAllLastNameDescending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        }
        return null;
    }

    private Slice<UserDto> handleUserName(SortingAndFilteringDto sortingAndFilteringDto, CassandraPageRequest request) {
        if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("ASC")) {
            return modelMapper.map(userByUserNameRepository.findAllUsernameAscending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        } else if (sortingAndFilteringDto.getOrderDirection().equalsIgnoreCase("DESC")) {
            return modelMapper.map(userByUserNameRepository.findAllUsernameDescending(request),
                    new TypeToken<Slice<UserDto>>() {
                    }.getType());
        }
        return null;
    }
}
