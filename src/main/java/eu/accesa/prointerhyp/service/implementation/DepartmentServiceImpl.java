package eu.accesa.prointerhyp.service.implementation;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import eu.accesa.prointerhyp.model.UserEntity;
import eu.accesa.prointerhyp.model.dto.DepartmentDto;
import eu.accesa.prointerhyp.model.dto.UserToDepartmentDto;
import eu.accesa.prointerhyp.repository.DepartmentRepository;
import eu.accesa.prointerhyp.repository.UserRepository;
import eu.accesa.prointerhyp.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, UserRepository userRepository, ModelMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DepartmentDto> addUserToDepartment(String department, UUID userId) {
        List<DepartmentEntity> departmentEntity = departmentRepository.findByNameEquals(department);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        List<DepartmentEntity> deptEntities = new ArrayList<>();

        if (!departmentEntity.isEmpty() && userEntity != null) {
            DepartmentEntity dept = new DepartmentEntity();

            BeanUtils.copyProperties(departmentEntity.get(0), dept);

            dept.setUserId(userId);
            departmentRepository.save(dept);
            deptEntities = departmentRepository.findAllByNameEquals(department);
        }

        if (deptEntities.isEmpty()) return new ArrayList<>();

        return deptEntities.stream()
                .map(dpt -> mapper.map(dpt, DepartmentDto.class))
                .collect(toList());
    }

    @Override
    public void deleteUserFromDepartment(UserToDepartmentDto dto) {
        UserEntity userEntity = userRepository.findById(dto.getUserId()).orElse(null);
        List<DepartmentEntity> departmentById = departmentRepository.findAllByUserIdEquals(dto.getUserId());

        for (DepartmentEntity dep: departmentById) {
            if (userEntity != null) {
                departmentRepository.deleteByNameAndUserId(dto.getDepartment(), dto.getUserId());
            }
        }

    }
}
