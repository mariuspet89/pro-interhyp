package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByDetailsEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface UserByDetailsRepository extends CassandraRepository<UserByDetailsEntity, UUID> {

    @Query("SELECT * FROM users_by_details WHERE company = 'accesa' ORDER BY details ASC")
    Slice<UserByDetailsEntity> findAllDetailsAscending(CassandraPageRequest request);

    @Query("SELECT * FROM users_by_details")
    Slice<UserByDetailsEntity> findAllDetailsDescending(CassandraPageRequest request);
}
