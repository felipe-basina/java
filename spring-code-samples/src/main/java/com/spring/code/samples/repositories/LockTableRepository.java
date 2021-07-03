package com.spring.code.samples.repositories;

import com.spring.code.samples.entities.LockTableEntity;
import org.springframework.data.repository.CrudRepository;

public interface LockTableRepository extends CrudRepository<LockTableEntity, Long> {
}
