package com.spring.code.samples.dao;

import com.spring.code.samples.entities.LockTableEntity;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LockTableDao {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public LockTableEntity getAndLock() {
        LockTableEntity entity = this.em.find(LockTableEntity.class, 1L);
        this.em.lock(entity, LockModeType.PESSIMISTIC_WRITE);
        entity.setDatLock(LocalDateTime.now());
        this.em.merge(entity);
        LOGGER.info(String.format("Entity updated e=%s", entity));
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public LockTableEntity getAndLock2() {
        List<LockTableEntity> list = this.em
                .createQuery("SELECT l FROM LockTable l WHERE l.id = 1")
                //.setHint("javax.persistence.lock.timeout", 15000)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
        this.em.lock(list.get(0), LockModeType.PESSIMISTIC_WRITE);
        this.em.merge(list.get(0));
        return list.get(0);
    }

    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public LockTableEntity getAndLock3() {
        EntityManager localEntityManager = this.emf.createEntityManager();
        EntityTransaction tx = null;
        Session session = null;

        LockTableEntity entity = null;
        try {
            session = localEntityManager.unwrap(Session.class);
            tx = localEntityManager.getTransaction();

            tx.begin();

            //entity = localEntityManager.find(LockTableEntity.class, 1L);
            entity = session.get(LockTableEntity.class, 1L, LockOptions.UPGRADE);

            entity.setDatLock(LocalDateTime.now());
            localEntityManager.merge(entity);
            LOGGER.info(String.format("Entity updated e=%s", entity));
        } catch (Exception ex) {
            LOGGER.error("m=getAndLock3", ex);
            tx.rollback();
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
            if (Objects.nonNull(tx)) {
                tx.commit();
            }
            if (Objects.nonNull(localEntityManager)) {
                localEntityManager.close();
            }
        }

        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public LockTableEntity getAndLock4() {
        LockTableEntity entity = this.em.find(LockTableEntity.class, 1L);

        this.em.unwrap(Session.class)
                .getSession()
                .buildLockRequest(LockOptions.UPGRADE)
                .setLockMode(LockMode.PESSIMISTIC_WRITE)
                .lock(entity);

        entity.setDatLock(LocalDateTime.now());
        this.em.merge(entity);
        LOGGER.info(String.format("Entity updated 4 e=%s", entity));
        return entity;
    }

}
