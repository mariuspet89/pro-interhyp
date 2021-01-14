package eu.accesa.prointerhyp.repository;

import eu.accesa.prointerhyp.model.UserByBirthdayEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface UserByBirthdayRepository extends CassandraRepository<UserByBirthdayEntity, UUID> {

    @Query("SELECT * FROM users_by_birthday WHERE company = 'accesa' ORDER BY birthday ASC")
    Slice<UserByBirthdayEntity> findAllBirthdayAscending(CassandraPageRequest request);

    @Query("SELECT * FROM users_by_birthday")
    Slice<UserByBirthdayEntity> findAllBirthdayDescending(CassandraPageRequest request);
}
