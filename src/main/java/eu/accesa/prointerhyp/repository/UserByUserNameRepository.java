package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByUserNameEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface UserByUserNameRepository extends CassandraRepository<UserByUserNameEntity, UUID> {

    @Query("SELECT * FROM users_by_username WHERE company = 'accesa' ORDER BY username ASC")
    Slice<UserByUserNameEntity> findAllUsernameAscending(CassandraPageRequest request);

    @Query("SELECT * FROM users_by_username")
    Slice<UserByUserNameEntity> findAllUsernameDescending(CassandraPageRequest request);
}
