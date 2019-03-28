package br.com.exemplo.spring.diversos.bulk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkRepository extends JpaRepository<BulkEntity, Long> {

}
