package com.karmanno.txexample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SomeEntityRepository extends JpaRepository<SomeEntity, String> {

    List<SomeEntity> findAllByTimeBetween(LocalDateTime first, LocalDateTime second);

    @Query(value = "from SomeEntity e where e.time between :first and :second")
    List<SomeEntity> getByTimeBetween(@Param("first") LocalDateTime first, @Param("second") LocalDateTime second);

}
