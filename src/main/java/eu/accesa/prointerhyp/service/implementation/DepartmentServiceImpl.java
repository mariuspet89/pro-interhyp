package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;
import eu.accesa.prointerhyp.repository.DepartmentRepository;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 UserRepository userRepository,
                                 ModelMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getAllUsersInDepartment(String department) {
        //TODO create a user UDT and change users field in department table to list<user_type>.
        //TODO After that, this method can return a DepartmentDto which contains department details and ->
        //TODO a list of complete users with details in users field

        List<UserDto> userDtos = new ArrayList<>();
        for (UUID userId : departmentRepository.findByNameEquals(department).getUserIds()) {
            userRepository.findById(userId).ifPresent(user -> userDtos.add(mapper.map(user, UserDto.class)));
        }
        return userDtos;
    }

    @Override
    public DepartmentDto addUserToDepartment(UserToDepartmentDto dto) {
        DepartmentEntity departmentEntity = departmentRepository.findByNameEquals(dto.getDepartment());
        UserEntity userEntity = userRepository.findById(dto.getUserId()).orElse(null);

        if (departmentEntity != null && userEntity != null) {
            List<UUID> userIds = departmentEntity.getUserIds();
            if (userIds == null) {
                userIds = new ArrayList<>();
            }

            userIds.add(dto.getUserId());
            departmentEntity.setUserIds(userIds);
            departmentEntity.setSize(departmentEntity.getUserIds().size());

            departmentRepository.save(departmentEntity);
        }
        return mapper.map(departmentRepository.findByNameEquals(dto.getDepartment()), DepartmentDto.class);
    }

    @Override
    public void deleteUserFromDepartment(UserToDepartmentDto dto) {
        DepartmentEntity departmentEntity = departmentRepository.findByNameEquals(dto.getDepartment());

        if (departmentEntity != null && !departmentEntity.getUserIds().isEmpty()) {
            List<UUID> userIds = departmentEntity.getUserIds();
            for (UUID userId : userIds) {
                if (userId.equals(dto.getUserId())) {

                    userIds.remove(dto.getUserId());

                    departmentEntity.setUserIds(userIds);
                    departmentEntity.setSize(departmentEntity.getUserIds().size());
                    departmentRepository.save(departmentEntity);
                }
            }
        }
    }
}
