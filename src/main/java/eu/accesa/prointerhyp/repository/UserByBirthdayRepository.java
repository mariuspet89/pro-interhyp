package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByDetailsEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface UserByBirthdayRepository extends CassandraRepository<UserByDetailsEntity, UUID> {

    List<UserByDetailsEntity> findAll();
}
