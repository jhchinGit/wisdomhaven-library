package com.wisdomhaven.library.borrower.data;

import com.wisdomhaven.library.borrower.model.Borrower;
import com.wisdomhaven.library.borrower.repository.BorrowerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDatabase {
    @Bean
    CommandLineRunner setupMockData(BorrowerRepository borrowerRepository) {
        return args -> {
            setupBorrower(borrowerRepository);
        };
    }

    private void setupBorrower(BorrowerRepository borrowerRepository) {
        if (borrowerRepository.count() == 0) {
            borrowerRepository.save(Borrower.builder().name("John Smith").email("john.smith@example.com").build());
            borrowerRepository.save(Borrower.builder().name("Emily Johnson").email("emily.johnson@example.com").build());
            borrowerRepository.save(Borrower.builder().name("Michael Brown").email("michael.brown@example.com").build());
            borrowerRepository.save(Borrower.builder().name("Sarah Davis").email("sarah.davis@example.com").build());
            borrowerRepository.save(Borrower.builder().name("David Wilson").email("david.wilson@example.com").build());
        }
    }
}
