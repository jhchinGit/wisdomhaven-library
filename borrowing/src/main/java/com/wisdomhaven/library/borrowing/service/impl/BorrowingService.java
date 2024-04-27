package com.wisdomhaven.library.borrowing.service.impl;

import com.wisdomhaven.library.borrowing.converter.BorrowingConverter;
import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.response.BorrowingResponseDTO;
import com.wisdomhaven.library.borrowing.model.Borrowing;
import com.wisdomhaven.library.borrowing.model.BorrowingDetail;
import com.wisdomhaven.library.borrowing.repository.BorrowingDetailRepository;
import com.wisdomhaven.library.borrowing.repository.BorrowingRepository;
import com.wisdomhaven.library.borrowing.service.IBookService;
import com.wisdomhaven.library.borrowing.service.IBorrowingService;
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

    private final IBookService bookService;
    private final BorrowingRepository borrowingRepository;
    private final BorrowingDetailRepository borrowingDetailRepository;

    @Autowired
    public BorrowingService(IBookService bookService,
                            BorrowingRepository borrowingRepository,
                            BorrowingDetailRepository borrowingDetailRepository) {
        this.bookService = bookService;
        this.borrowingRepository = borrowingRepository;
        this.borrowingDetailRepository = borrowingDetailRepository;
    }

    @Override
    @Transactional
    public BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList) {
        validateBookIdListDuplication(bookIdList);
//        Borrower borrower = getBorrower(borrowerId);
        List<Book> availableBooks = getAvailableBooks(bookIdList);
        List<BorrowingDetail> borrowingDetails = availableBooks
                .stream()
                .map(BorrowingConverter::toBorrowingDetail)
                .toList();
//        validateRedundantBookBorrowing(borrowerId, availableBooks, borrower);

        Borrowing borrowing = this.borrowingRepository.save(
                Borrowing
                        .builder()
//                        .borrower(borrower)
                        .borrowingDetails(borrowingDetails)
                        .isFullyReturn(false)
                .build());
        borrowingDetails.forEach(b -> b.setBorrowing(borrowing));
        borrowingDetails = this.borrowingDetailRepository.saveAll(borrowingDetails);

        return BorrowingResponseDTO
                .builder()
                .borrowingId(borrowing.getBorrowingId())
                .details(borrowingDetails
                        .stream()
                        .map(BorrowingConverter::toBorrowingDetailResponseDTO)
                        .toList())
                .build();
    }

    @Override
    public String returnAllBooks(Integer borrowingId) {
        return null;
    }

    @Override
    public String returnBookByBookId(Integer borrowingId, Integer bookId) {
        return null;
    }
//    @Override
//    @Transactional
//    public String returnAllBooks(Integer borrowingId) {
//        Borrowing borrowing = this.borrowingRepository.findById(borrowingId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        String.format("Borrowing %d not found.", borrowingId)
//                ));
//
//        if (borrowing.isFullyReturn()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "No book remains unreturned."
//            );
//        }
//
//        List<Book> books = this.bookRepository.findByTransactionTransactionId(borrowingId);
//        books.forEach(b -> b.setBorrowing(null));
//        this.bookRepository.saveAll(books);
//
//        borrowing.setFullyReturn(true);
//        this.borrowingRepository.save(borrowing);
//
//        return "All books have been returned.";
//    }
//
//    @Override
//    public String returnBookByBookId(Integer borrowingId, Integer bookId) {
//        Book book = this.bookRepository
//                .findByTransactionTransactionIdAndBookId(borrowingId, bookId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        String.format("The book with the book id %d either cannot be located or has not been borrowed.", bookId)
//                ));
//
//        book.setBorrowing(null);
//        this.bookRepository.save(book);
//        return String.format("Book id %d is returned.", bookId);
//    }
//
    private static void validateBookIdListDuplication(List<Integer> bookIdList) {
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
//
//    private Borrower getBorrower(Integer borrowerId) {
//        return this.borrowerRepository
//                .findById(borrowerId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        String.format("Borrower Id %d not found.", borrowerId)));
//    }
//
    private List<Book> getAvailableBooks(List<Integer> bookIdList) {
        List<Book> books = this.bookService.getListOfBooksByIds(bookIdList);
        List<Integer> foundBookIdList = books
                .stream()
                .filter(Book::isAvailable)
                .map(Book::bookId)
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

        return books;
    }
//
//    private static void validateRedundantBookBorrowing(Integer borrowerId,
//                                                       List<Book> availableBooks,
//                                                       Borrower borrower) {
//        List<String> availableBookIsbnList = availableBooks
//                .stream()
//                .map(Book::getIsbn)
//                .toList();
//
//        Set<String> sameIsbnBorrowedBookTitle = new TreeSet<>();
//        borrower.getTransactionList()
//                .stream()
//                .filter(t -> !t.isFullyReturn())
//                .forEach(t -> sameIsbnBorrowedBookTitle.addAll(t.getBooks()
//                        .stream()
//                        .filter(b -> availableBookIsbnList.contains(b.getIsbn()))
//                        .map(Book::getTitle)
//                        .toList()));
//
//        if (!sameIsbnBorrowedBookTitle.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    String.format("Borrower with id %d has borrowed book with book title [%s].",
//                            borrowerId,
//                            String.join(", ", sameIsbnBorrowedBookTitle)));
//        }
//    }
}
