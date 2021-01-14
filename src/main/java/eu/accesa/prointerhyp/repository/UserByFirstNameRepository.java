package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByFirstNameEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface UserByFirstNameRepository extends CassandraRepository<UserByFirstNameEntity, UUID> {

    @Query("SELECT * FROM users_by_first_name WHERE company = 'accesa' ORDER BY first_name ASC")
    Slice<UserByFirstNameEntity> findAllFirstNameAscending(CassandraPageRequest request);

    @Query("SELECT * FROM users_by_first_name")
    Slice<UserByFirstNameEntity> findAllFirstNameDescending(CassandraPageRequest request);
}
