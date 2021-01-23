package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.DepartmentEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface DepartmentRepository extends CassandraRepository<DepartmentEntity, UUID> {

    DepartmentEntity findByNameEquals(String name);
}
