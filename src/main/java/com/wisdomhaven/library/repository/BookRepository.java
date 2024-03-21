package com.wisdomhaven.library.repository;

import com.wisdomhaven.library.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query("""
            SELECT b
            FROM Book b
            WHERE (:bookId IS NULL OR b.id = :bookId) AND
            (:title IS NULL OR b.title = :title) AND
            (:author IS NULL OR b.author = :author) AND
            (:isbn IS NULL OR b.isbn = :isbn)
            """)
    List<Book> findByIdOrTitleOrAuthorOrIsbn(@Param("bookId") Integer id,
                                             @Param("title") String title,
                                             @Param("author") String author,
                                             @Param("isbn") String isbn);
}