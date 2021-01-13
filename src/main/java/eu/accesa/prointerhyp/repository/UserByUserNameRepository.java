package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByUserNameEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface UserByUserNameRepository extends CassandraRepository<UserByUserNameEntity, UUID> {

    List<UserByUserNameEntity> findAll();
}
