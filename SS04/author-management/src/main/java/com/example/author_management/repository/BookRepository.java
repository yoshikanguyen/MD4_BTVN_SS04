package com.example.author_management.repository;

import com.example.author_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);

    @Query("SELECT b FROM Book b WHERE b.price > (SELECT AVG(bk.price) FROM Book bk)")
    List<Book> findBooksWithPriceHigherThanAverage();

    @Query(value = "SELECT a.name AS authorName, COUNT(b.id) AS bookCount FROM authors a LEFT JOIN books b ON a.id = b.author_id GROUP BY a.id, a.name", nativeQuery = true)
    List<Object[]> getBookStatisticsByAuthor();
}
