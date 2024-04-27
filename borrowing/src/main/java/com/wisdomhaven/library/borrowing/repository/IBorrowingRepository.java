package com.wisdomhaven.library.borrowing.repository;

import com.wisdomhaven.library.borrowing.model.Borrowing;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IBorrowingRepository extends ListCrudRepository<Borrowing, Integer> {
    List<Borrowing> findByBorrowerIdAndIsFullyReturnFalse(Integer borrowerId);
}
