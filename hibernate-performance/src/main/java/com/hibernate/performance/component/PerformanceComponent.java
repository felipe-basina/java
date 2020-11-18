package com.hibernate.performance.component;

import com.hibernate.performance.domain.Author;
import com.hibernate.performance.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.stream.IntStream;

@Slf4j
@Component
public class PerformanceComponent {

    private final EntityManager entityManager;

    @Autowired
    public PerformanceComponent(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createData(final int totalAuthor, final int totalBooks) {
        IntStream.rangeClosed(1, totalAuthor).forEach(index -> {
            final Author author = Author.builder()
                    .firstName("Name_" + index)
                    .lastName("Lastname_" + index)
                    .build();
            this.entityManager.persist(author);

            author.setBookList(new ArrayList<>());

            IntStream.rangeClosed(1, totalBooks).forEach(innerIndex -> {
              final Book book = Book.builder()
                      .title("A title " + innerIndex)
                      .author(author)
                      .build();
              this.entityManager.persist(book);
              author.getBookList().add(book);
            });

            this.entityManager.merge(author);
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteData() {
        final String deleteFromBook = "DELETE from ".concat(Book.class.getSimpleName());
        final int removedBooks = this.entityManager.createQuery(deleteFromBook).executeUpdate();

        final String deleteFromAuthor = "DELETE from ".concat(Author.class.getSimpleName());
        final int removedAuthors = this.entityManager.createQuery(deleteFromAuthor).executeUpdate();

        log.info("Total removed data for author: {} | books: {}", removedAuthors, removedBooks);
    }

}
