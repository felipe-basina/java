package com.spring.code.samples.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name = "LockTable")
@Table(name = "lock_table")
public class LockTableEntity implements Serializable {

    @Id
    private Long id;

    @Column(name = "dat_lock")
    private LocalDateTime datLock;

}
