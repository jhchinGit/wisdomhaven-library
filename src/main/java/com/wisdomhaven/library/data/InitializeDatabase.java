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
            bookRepository.save(Book.builder().title("The Great Gatsby").author("F. Scott Fitzgerald").isbn("9780743273565").build());
            bookRepository.save(Book.builder().title("The Great Gatsby").author("F. Scott Fitzgerald").isbn("9780743273565").build());
            bookRepository.save(Book.builder().title("To Kill a Mockingbird").author("Harper Lee").isbn("9780061120084").build());
            bookRepository.save(Book.builder().title("To Kill a Mockingbird").author("Harper Lee").isbn("9780061120084").build());
            bookRepository.save(Book.builder().title("To Kill a Mockingbird").author("Harper Lee").isbn("9780061120084").build());
            bookRepository.save(Book.builder().title("1984").author("George Orwell").isbn("9780451524935").build());
            bookRepository.save(Book.builder().title("Pride and Prejudice").author("Jane Austen").isbn("9780141439518").build());
            bookRepository.save(Book.builder().title("The Catcher in the Rye").author("J.D. Salinger").isbn("9780316769488").build());
            bookRepository.save(Book.builder().title("Harry Potter and the Sorcerer's Stone").author("J.K. Rowling").isbn("9780590353427").build());
            bookRepository.save(Book.builder().title("The Hobbit").author("J.R.R. Tolkien").isbn("9780547928227").build());
            bookRepository.save(Book.builder().title("The Lord of the Rings").author("J.R.R. Tolkien").isbn("9780544003415").build());
        };
    }
}
