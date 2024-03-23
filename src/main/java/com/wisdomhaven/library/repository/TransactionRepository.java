package com.wisdomhaven.library.repository;

import com.wisdomhaven.library.model.Transaction;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Integer> {

}
