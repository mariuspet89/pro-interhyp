package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.DepartmentDtoForGet;
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
    public List<DepartmentDtoForGet> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<DepartmentDtoForGet> departmentDtos = new ArrayList<>();

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
    public DepartmentDtoForGet getAllUsersInDepartment(String department) {
        DepartmentEntity departmentEntity = departmentRepository.findByNameEquals(department);
        List<UUID> userIds = departmentEntity.getUserIds();
        List<UserDto> userDtos = new ArrayList<>();

        if (userIds == null) userIds = new ArrayList<>();

        for (UUID user : userIds) {
            userRepository.findById(user)
                    .ifPresent(userEntityFromRepo -> userDtos.add(mapper.map(userEntityFromRepo, UserDto.class)));
        }
        DepartmentDtoForGet departmentDto = mapper.map(departmentEntity, DepartmentDtoForGet.class);

        departmentDto.setUserDtos(userDtos);
        return departmentDto;
    }

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {

        DepartmentEntity departmentEntity = mapper.map(departmentDto, DepartmentEntity.class);
        if (!departmentDto.getName().equals(departmentRepository.findByNameEquals(departmentDto.getName()))) {
            departmentEntity.setName(departmentDto.getName());
            departmentEntity.setSize((0));
        } else {
            return mapper.map(departmentRepository.findByNameEquals(departmentDto.getName()), DepartmentDto.class);
        }
        return mapper.map(departmentRepository.save(departmentEntity), DepartmentDto.class);
    }

    @Override
    public DepartmentDtoForGet addUserToDepartment(UserToDepartmentDto dto) {
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
