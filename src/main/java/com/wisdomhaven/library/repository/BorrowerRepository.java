package com.wisdomhaven.library.repository;

import com.wisdomhaven.library.model.Borrower;
import org.springframework.data.repository.CrudRepository;

public interface BorrowerRepository extends CrudRepository<Borrower, Integer> {
}
