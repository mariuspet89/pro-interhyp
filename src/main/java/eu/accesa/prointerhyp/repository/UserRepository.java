package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CassandraRepository<UserEntity, UUID> {

    List<UserEntity> findAll();

    @AllowFiltering
    Optional<UserEntity> findById(@NotNull UUID userId);

    void deleteByIdAndCompany(UUID uuid, String company);

    UserEntity findByIdAndCompany(UUID id, String company);
}
