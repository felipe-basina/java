package com.spring.code.samples.repositories;

import com.spring.code.samples.entities.LockTableEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface LockTableRepository extends CrudRepository<LockTableEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select e from LockTable e where e.id = :id")
    LockTableEntity findByIdAndLock(@Param(value = "id") Long id);

}
