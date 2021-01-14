package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByLastNameEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface UserByLastNameRepository extends CassandraRepository<UserByLastNameEntity, UUID> {

    @Query("SELECT * FROM users_by_last_name WHERE company = 'accesa' ORDER BY last_name ASC")
    Slice<UserByLastNameEntity> findAllLastNameAscending(CassandraPageRequest request);

    @Query("SELECT * FROM users_by_last_name")
    Slice<UserByLastNameEntity> findAllLastNameDescending(CassandraPageRequest request);
}
