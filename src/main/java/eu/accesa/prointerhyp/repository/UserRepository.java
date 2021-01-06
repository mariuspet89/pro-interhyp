package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<UserEntity,UUID > {

    List<UserEntity> findAll();
    @AllowFiltering
    Optional<UserEntity> findById(UUID id);

}
