package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface DepartmentRepository extends CassandraRepository<DepartmentEntity, UUID> {

    DepartmentEntity findByNameEquals(String departmentName);

    void deleteByName(String departmentName);
    @AllowFiltering
    DepartmentEntity findByUserIdsContaining(UUID uuid);
}
