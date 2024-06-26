package com.wisdomhaven.library.book.repository;

import com.wisdomhaven.library.book.model.Book;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Integer> {

    @Query(value = """
            SELECT b
            FROM Book b
            WHERE (:bookId IS NULL OR b.bookId = :bookId) AND
            (:title IS NULL OR LOWER(b.title) LIKE '%' || LOWER(:title) || '%') AND
            (:author IS NULL OR LOWER(b.author) LIKE '%' || LOWER(:author) || '%') AND
            (:isbn IS NULL OR b.isbn = :isbn)
            """,
    countQuery = """
            SELECT count(b)
            FROM Book b
            WHERE (:bookId IS NULL OR b.bookId = :bookId) AND
            (:title IS NULL OR LOWER(b.title) LIKE '%' || LOWER(:title) || '%') AND
            (:author IS NULL OR LOWER(b.author) LIKE '%' || LOWER(:author) || '%') AND
            (:isbn IS NULL OR b.isbn = :isbn)
            """)
    Page<Book> findByBookIdOrTitleOrAuthorOrIsbn(@Param("bookId") @Nullable Integer bookId,
                                                 @Param("title") @Nullable String title,
                                                 @Param("author") @Nullable String author,
                                                 @Param("isbn") @Nullable String isbn,
                                                 Pageable pageable);

    List<Book> findByIsbn(String isbn);
}