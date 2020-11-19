package com.hibernate.performance.service;

import com.hibernate.performance.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
public class PerformanceService {

    private final EntityManager em;

    @Autowired
    public PerformanceService(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void selectingAnEntity() {
        long timeTx = 0;
        long timeQuery = 0;
        long iterations = 1000;

        // Perform 1000 iterations
        for (int i = 0; i < iterations; i++) {
            long startTx = System.currentTimeMillis();
            //em.getTransaction().begin();

            // Execute Query
            long startQuery = System.currentTimeMillis();
            List<Book> books = em.createQuery("SELECT b FROM Book b").getResultList();
            long endQuery = System.currentTimeMillis();
            timeQuery += endQuery - startQuery;

            //em.getTransaction().commit();
            this.em.flush();
            long endTx = System.currentTimeMillis();

            //em.close();
            timeTx += endTx - startTx;
        }

        log.info("ENTITY :: Transaction: total {} per iteration {}", timeTx, (timeTx / (double)iterations));
        log.info("ENTITY :: Query: total {} per iteration {}", timeQuery, (timeQuery / (double)iterations));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void selectingADomain() {
        long timeTx = 0;
        long timeQuery = 0;
        long iterations = 1000;

        // Perform 1000 iterations
        for (int i = 0; i < iterations; i++) {
            long startTx = System.currentTimeMillis();
            //em.getTransaction().begin();

            // Execute Query
            long startQuery = System.currentTimeMillis();
            List<Book> books = em
                    .createQuery("SELECT new com.hibernate.performance.domain.Book(b.id, b.title) FROM Book b")
                    .getResultList();
            long endQuery = System.currentTimeMillis();
            timeQuery += endQuery - startQuery;

            //em.getTransaction().commit();
            this.em.flush();
            long endTx = System.currentTimeMillis();

            //em.close();
            timeTx += endTx - startTx;
        }

        log.info("DTO :: Transaction: total {} per iteration {}", timeTx, (timeTx / (double)iterations));
        log.info("DTO :: Query: total {} per iteration {}", timeQuery, (timeQuery / (double)iterations));
    }

}
