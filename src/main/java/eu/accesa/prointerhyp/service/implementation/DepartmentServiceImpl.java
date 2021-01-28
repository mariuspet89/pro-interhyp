package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.DepartmentWithUsersDto;
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
    public List<DepartmentWithUsersDto> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<DepartmentWithUsersDto> departmentDtos = new ArrayList<>();

        for (DepartmentEntity department : departmentEntities) {
            departmentDtos.add(getAllUsersInDepartment(department.getName()));
        }
        return departmentDtos;
    }

    @Override
    public void deleteDepartment(String departmentName) {
        departmentRepository.deleteByName(departmentName);
    }

    @Override
    public DepartmentWithUsersDto getAllUsersInDepartment(String department) {
        DepartmentEntity departmentEntity = departmentRepository.findByNameEquals(department);
        List<UUID> userIds = departmentEntity.getUserIds();
        List<UserDto> userDtos = new ArrayList<>();

        if (userIds == null) userIds = new ArrayList<>();

        for (UUID user : userIds) {
            userRepository.findById(user)
                    .ifPresent(userEntityFromRepo -> userDtos.add(mapper.map(userEntityFromRepo, UserDto.class)));
        }
        DepartmentWithUsersDto departmentDto = mapper.map(departmentEntity, DepartmentWithUsersDto.class);

        departmentDto.setUserDtos(userDtos);
        return departmentDto;
    }

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {

        if (departmentRepository.findByNameEquals(departmentDto.getName().toUpperCase()) != null) {
            return mapper.map(departmentRepository.findByNameEquals(departmentDto.getName().toUpperCase()), DepartmentDto.class);
        } else {
            DepartmentEntity departmentEntity = mapper.map(departmentDto, DepartmentEntity.class);
            departmentEntity.setName(departmentDto.getName());
            departmentEntity.setSize((0));
            return mapper.map(departmentRepository.save(departmentEntity), DepartmentDto.class);
        }
    }

    @Override
    public DepartmentDto findDepartmentByUserIdContaining(UUID uuid) {
        return mapper.map(departmentRepository.findByUserIdsContaining(uuid).
                orElseThrow(()-> new NullPointerException("user is not enrolled in any Department...therefore it has been deleted from Users List")), DepartmentDto.class);
    }

    @Override
    public DepartmentWithUsersDto addUserToDepartment(UserToDepartmentDto dto) {
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
        return getAllUsersInDepartment(dto.getDepartment());
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
                    break;
                }
            }
        }
    }
}
