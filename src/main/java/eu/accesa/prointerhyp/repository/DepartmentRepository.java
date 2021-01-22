package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends CassandraRepository<DepartmentEntity, UUID> {

    List<DepartmentEntity> findByNameEquals(String name);

    List<DepartmentEntity> findAllByNameEquals(String name);

    @AllowFiltering
    List<DepartmentEntity> findAllByUserIdEquals(UUID userId);

    void deleteByNameAndUserId(String name, UUID userId);
}
