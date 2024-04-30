package com.wisdomhaven.library.borrower.repository;

import com.wisdomhaven.library.borrower.model.Borrower;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface IBorrowerRepository extends ListCrudRepository<Borrower, Integer> {
    @Query(value = """
            SELECT b
            FROM Borrower b
            WHERE (:borrowerId IS NULL OR b.borrowerId = :borrowerId) AND
            (:name IS NULL OR LOWER(b.name) LIKE '%' || LOWER(:name) || '%') AND
            (:email IS NULL OR LOWER(b.email) = LOWER(:email))
            """,
    countQuery = """
            SELECT count(*)
            FROM Borrower b
            WHERE (:borrowerId IS NULL OR b.borrowerId = :borrowerId) AND
            (:name IS NULL OR LOWER(b.name) LIKE '%' || LOWER(:name) || '%') AND
            (:email IS NULL OR LOWER(b.email) = LOWER(:email))
            """)
    Page<Borrower> findByBorrowerIdOrNameOrEmail(@Param("borrowerId") @Nullable Integer borrowerId,
                                                 @Param("name") @Nullable String name,
                                                 @Param("email") @Nullable String email,
                                                 Pageable pageable);

    boolean existsByEmailIgnoreCase(@Nullable String email);
}
