package com.wisdomhaven.library.data;

import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDatabase {
    @Bean
    CommandLineRunner setupMockData(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(Book.builder().title("The Great Gatsby").author("F. Scott Fitzgerald").isbn("9780743273565").build());
            bookRepository.save(Book.builder().title("To Kill a Mockingbird").author("Harper Lee").isbn("9780061120084").build());
            bookRepository.save(Book.builder().title("1984").author("George Orwell").isbn("9780451524935").build());
        };
    }
}
