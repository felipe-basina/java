package com.hibernate.performance.service;

import com.hibernate.performance.domain.Author;
import com.hibernate.performance.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void getAll() {
        long timeTx = 0;
        long timeQuery = 0;
        long iterations = 1000;

        // Perform 1000 iterations
        for (int i = 0; i < iterations; i++) {
            long startTx = System.currentTimeMillis();
            //em.getTransaction().begin();

            // Execute Query
            long startQuery = System.currentTimeMillis();
            List<Author> authors = this.em.createQuery("FROM Author a", Author.class)
                    .getResultList();
            for (Author author : authors) {
                author.getBookList().forEach(book ->
                        log.info("Book id {}, author id {}", book.getId(), author.getId()));
            }
            long endQuery = System.currentTimeMillis();
            timeQuery += endQuery - startQuery;

            //em.getTransaction().commit();
            this.em.flush();
            long endTx = System.currentTimeMillis();

            //em.close();
            timeTx += endTx - startTx;
        }

        log.info("all :: Transaction: total {} per iteration {}", timeTx, (timeTx / (double)iterations));
        log.info("all :: Query: total {} per iteration {}", timeQuery, (timeQuery / (double)iterations));
    }

    public void getWithDynamicEntityGraph() {
        EntityGraph<Author> graph = this.em.createEntityGraph(Author.class);
        Subgraph<Book> bookGraph = graph.addSubgraph("bookList", Book.class);

        Map<String, Object> hints = new HashMap<>();
        /**
         * eagerly load entities that are normally loaded via lazy annotation,
         * add entities to the query results that would normally be loaded later
         * (thereby avoiding specific N+1 cases)
         */
        hints.put("javax.persistence.loadgraph", graph);

        /**
         * all relationships are considered to be lazy regardless of annotation,
         * and only the elements of the provided graph are loaded
         */
        //hints.put("javax.persistence.fetchgraph", graph);

        Integer maxId = (Integer) this.em.createNativeQuery("SELECT MAX(id) FROM author")
                .getSingleResult();

        long startQuery = System.currentTimeMillis();
        Author author = this.em.find(Author.class, maxId.longValue(), hints);
        long endQuery = System.currentTimeMillis();

        /**
         * GENERATED SQL
         *
         * SELECT author0_.id          AS id1_0_0_,
         *        author0_.first_name  AS first_na2_0_0_,
         *        author0_.last_name   AS last_nam3_0_0_,
         *        author0_.version     AS version4_0_0_,
         *        booklist1_.author_id AS author_i4_1_1_,
         *        booklist1_.id        AS id1_1_1_,
         *        booklist1_.id        AS id1_1_2_,
         *        booklist1_.author_id AS author_i4_1_2_,
         *        booklist1_.title     AS title2_1_2_,
         *        booklist1_.version   AS version3_1_2_
         * FROM   author author0_
         *        LEFT OUTER JOIN book booklist1_
         *                     ON author0_.id = booklist1_.author_id
         * WHERE  author0_.id = ?
         */

        author.getBookList().forEach(book -> log.info("Book id {}, author id {}",
                book.getId(), book.getAuthor().getId()));

        log.info("Time query {}", (endQuery - startQuery));
    }

}
