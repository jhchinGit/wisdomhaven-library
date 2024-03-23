package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BookConverter;
import com.wisdomhaven.library.dto.response.BorrowingResponseDTO;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.model.Borrower;
import com.wisdomhaven.library.model.Transaction;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.repository.BorrowerRepository;
import com.wisdomhaven.library.repository.TransactionRepository;
import com.wisdomhaven.library.service.IBorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BorrowingService implements IBorrowingService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BorrowingService(BorrowerRepository borrowerRepository,
                            BookRepository bookRepository,
                            TransactionRepository transactionRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public BorrowingResponseDTO createBorrowing(Integer borrowerId, List<String> isbnList) {
        validationIsbnDuplication(isbnList);

        Borrower borrower = getBorrower(borrowerId);
        List<Book> uniqueIsbnBooks = getAvailableBooks(isbnList);

        Transaction transaction = this.transactionRepository.save(Transaction
                .builder()
                .borrower(borrower)
                .books(uniqueIsbnBooks)
                .build());
        uniqueIsbnBooks.forEach(b -> b.setTransaction(transaction));
        List<Book> borrowedBooks = this.bookRepository.saveAll(uniqueIsbnBooks);

        return BorrowingResponseDTO
                .builder()
                .transactionId(transaction.getTransactionId())
                .bookResponseDTOList(borrowedBooks
                        .stream()
                        .map(BookConverter::ToBookResponseDTO)
                        .toList())
                .build();
    }

    private void validationIsbnDuplication(List<String> isbnList) {
        String duplicateIsbn = isbnList
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));

        if (!duplicateIsbn.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Isbn [%s] is duplicated.", duplicateIsbn));
        }
    }

    private Borrower getBorrower(Integer borrowerId) {
        return this.borrowerRepository
                .findById(borrowerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Borrower Id %d not found.", borrowerId)));
    }

    private List<Book> getAvailableBooks(List<String> isbnList) {
        List<Book> uniqueIsbnBooks = this.bookRepository.findUnborrowedBookByIsbn(isbnList)
                .stream()
                .collect(Collectors.toMap(Book::getIsbn, b -> b, (existing, replacement) -> existing, TreeMap::new))
                .values()
                .stream()
                .toList();
        List<String> foundBookIsbnList = uniqueIsbnBooks
                .stream()
                .map(Book::getIsbn)
                .toList();
        String notFoundOrBorrowedIsbn = isbnList
                .stream()
                .filter(isbn -> !foundBookIsbnList.contains(isbn))
                .collect(Collectors.joining(", "));

        if (!notFoundOrBorrowedIsbn.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Isbn [%s] is not found or not available.", notFoundOrBorrowedIsbn));
        }

        return uniqueIsbnBooks;
    }
}
