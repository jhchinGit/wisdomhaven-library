package com.wisdomhaven.library.borrowing.repository;

import com.wisdomhaven.library.borrowing.model.Transaction;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Integer> {

}
