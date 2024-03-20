package com.wisdomhaven.library.repository;

import com.wisdomhaven.library.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

}