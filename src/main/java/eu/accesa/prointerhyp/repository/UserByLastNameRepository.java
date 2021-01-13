package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByLastNameEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface UserByLastNameRepository extends CassandraRepository<UserByLastNameEntity, UUID> {

    List<UserByLastNameEntity> findAll();
}
