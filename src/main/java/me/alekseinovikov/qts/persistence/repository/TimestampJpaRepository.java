package me.alekseinovikov.qts.persistence.repository;

import me.alekseinovikov.qts.persistence.model.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimestampJpaRepository extends JpaRepository<Timestamp, Long> {
}
