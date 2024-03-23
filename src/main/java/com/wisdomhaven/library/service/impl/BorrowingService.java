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
    public BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList) {
        validationBookDuplication(bookIdList);

        Borrower borrower = getBorrower(borrowerId);
        List<Book> availableBooks = getAvailableBooks(bookIdList);

        Transaction transaction = this.transactionRepository.save(Transaction
                .builder()
                .borrower(borrower)
                .books(availableBooks)
                .build());
        availableBooks.forEach(b -> b.setTransaction(transaction));
        List<Book> borrowedBooks = this.bookRepository.saveAll(availableBooks);

        return BorrowingResponseDTO
                .builder()
                .transactionId(transaction.getTransactionId())
                .bookResponseDTOList(borrowedBooks
                        .stream()
                        .map(BookConverter::ToBookResponseDTO)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public void returnAllBooks(Integer transactionId) {
        List<Book> books = this.bookRepository.findByTransactionTransactionId(transactionId);
        var a = "";
    }

    @Override
    public void returnBookByBookId(Integer transactionId, Integer bookId) {
        Book book = this.bookRepository
                .findByTransactionTransactionIdAndBookId(transactionId, bookId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("The book with the book id %d either cannot be located or has not been borrowed.", bookId)
                ));

        book.setTransaction(null);
        this.bookRepository.save(book);
    }

    private void validationBookDuplication(List<Integer> bookIdList) {
        String duplicateBookId = bookIdList
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> e.getKey().toString())
                .collect(Collectors.joining(", "));

        if (!duplicateBookId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Book id [%s] is duplicated.", duplicateBookId));
        }
    }

    private Borrower getBorrower(Integer borrowerId) {
        return this.borrowerRepository
                .findById(borrowerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Borrower Id %d not found.", borrowerId)));
    }

    private List<Book> getAvailableBooks(List<Integer> bookIdList) {
        List<Book> availableBooks = this.bookRepository.findAvailableBookByBookIds(bookIdList);
        List<Integer> foundBookIdList = availableBooks
                .stream()
                .map(Book::getBookId)
                .toList();
        String notFoundOrBorrowedBookId = bookIdList
                .stream()
                .filter(bookId -> !foundBookIdList.contains(bookId))
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        if (!notFoundOrBorrowedBookId.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Book id [%s] is not found or not available.", notFoundOrBorrowedBookId));
        }

        return availableBooks;
    }
}
