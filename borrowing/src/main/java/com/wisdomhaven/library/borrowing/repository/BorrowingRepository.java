package com.wisdomhaven.library.borrowing.repository;

import com.wisdomhaven.library.borrowing.model.Borrowing;
import org.springframework.data.repository.ListCrudRepository;

public interface BorrowingRepository extends ListCrudRepository<Borrowing, Integer> {

}
