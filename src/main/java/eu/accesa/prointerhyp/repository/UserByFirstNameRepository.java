package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByFirstNameEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface UserByFirstNameRepository extends CassandraRepository<UserByFirstNameEntity, UUID> {

    List<UserByFirstNameEntity> findAll();
}
