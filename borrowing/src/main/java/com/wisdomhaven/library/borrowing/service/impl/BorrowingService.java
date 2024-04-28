package com.wisdomhaven.library.borrowing.service.impl;

import com.wisdomhaven.library.borrowing.converter.BorrowingConverter;
import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.apiResult.Borrower;
import com.wisdomhaven.library.borrowing.dto.response.BorrowingResponseDTO;
import com.wisdomhaven.library.borrowing.model.Borrowing;
import com.wisdomhaven.library.borrowing.model.BorrowingDetail;
import com.wisdomhaven.library.borrowing.repository.IBorrowingDetailRepository;
import com.wisdomhaven.library.borrowing.repository.IBorrowingRepository;
import com.wisdomhaven.library.borrowing.service.IBookService;
import com.wisdomhaven.library.borrowing.service.IBorrowerService;
import com.wisdomhaven.library.borrowing.service.IBorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BorrowingService implements IBorrowingService {

    private final IBookService bookService;
    private final IBorrowerService borrowerService;
    private final IBorrowingRepository borrowingRepository;
    private final IBorrowingDetailRepository borrowingDetailRepository;

    @Autowired
    public BorrowingService(IBookService bookService,
                            IBorrowerService borrowerService,
                            IBorrowingRepository borrowingRepository,
                            IBorrowingDetailRepository borrowingDetailRepository) {
        this.bookService = bookService;
        this.borrowerService = borrowerService;
        this.borrowingRepository = borrowingRepository;
        this.borrowingDetailRepository = borrowingDetailRepository;
    }

    @Override
    @Transactional
    public BorrowingResponseDTO createBorrowing(Integer borrowerId, List<Integer> bookIdList) {
        validateBookIdListDuplication(bookIdList);
        Borrower borrower = this.borrowerService.getBorrowerById(borrowerId);
        List<Book> availableBooks = getAvailableBooks(bookIdList);
        List<BorrowingDetail> borrowingDetails = availableBooks
                .stream()
                .map(BorrowingConverter::toBorrowingDetail)
                .toList();
        validateRedundantBookBorrowing(borrower, availableBooks);

        bookIdList.forEach(bookId -> this.bookService.updateBookAvailability(bookId, false));

        Borrowing borrowing = this.borrowingRepository.save(
                Borrowing
                        .builder()
                        .borrowerId(borrowerId)
                        .borrowerName(borrower.name())
                        .borrowingDetails(borrowingDetails)
                .build());
        borrowingDetails.forEach(b -> b.setBorrowing(borrowing));
        borrowingDetails = this.borrowingDetailRepository.saveAll(borrowingDetails);

        return BorrowingResponseDTO
                .builder()
                .borrowingId(borrowing.getBorrowingId())
                .borrowerId(borrower.borrowerId())
                .borrowerName(borrower.name())
                .details(borrowingDetails
                        .stream()
                        .map(BorrowingConverter::toBorrowingDetailResponseDTO)
                        .toList())
                .build();
    }

    @Override
    @Transactional
    public String returnAllBooks(Integer borrowingId) {
        Borrowing borrowing = this.borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Borrowing %d not found.", borrowingId)
                ));

        if (borrowing.isFullyReturn()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No book remains unreturned."
            );
        }

        List<BorrowingDetail> toBeUpdatedBorrowingDetailList = new ArrayList<>();

        borrowing.getBorrowingDetails().forEach(bd -> {
            if (!bd.isReturn()) {
                this.bookService.updateBookAvailability(bd.getBookId(), true);
                bd.setReturn(true);
                toBeUpdatedBorrowingDetailList.add(bd);
            }
        });
        this.borrowingDetailRepository.saveAll(toBeUpdatedBorrowingDetailList);

        borrowing.setFullyReturn(true);
        this.borrowingRepository.save(borrowing);

        return "All books have been returned.";
    }

    @Override
    public String returnBookByBookId(Integer borrowingId, Integer bookId) {
        Borrowing borrowing = this.borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Borrowing %d not found.", borrowingId)
                ));

        BorrowingDetail borrowingDetail = borrowing
                .getBorrowingDetails()
                .stream()
                .filter(bd -> bd.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("The book with the book id %d either cannot be located or has not been borrowed.", bookId)
                ));

        if (borrowingDetail.isReturn()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The book has already returned."
            );
        }

        borrowingDetail.setReturn(true);
        this.bookService.updateBookAvailability(borrowingDetail.getBookId(), true);
        this.borrowingDetailRepository.save(borrowingDetail);

        boolean isAllBooksReturned = borrowing.getBorrowingDetails()
                .stream()
                .allMatch(BorrowingDetail::isReturn);

        if (isAllBooksReturned) {
            borrowing.setFullyReturn(true);
            this.borrowingRepository.save(borrowing);
        }

        return String.format("Book id %d is returned.", bookId);
    }

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

    private void validateRedundantBookBorrowing(Borrower borrower, List<Book> availableBooks) {
        List<String> availableBookIsbnList = availableBooks
                .stream()
                .map(Book::isbn)
                .toList();

        Set<String> sameIsbnBorrowedBookTitle = new TreeSet<>();
        this.borrowingRepository.findByBorrowerIdAndIsFullyReturnFalse(borrower.borrowerId())
                .stream()
                .filter(b -> !b.isFullyReturn())
                .forEach(b -> sameIsbnBorrowedBookTitle.addAll(b.getBorrowingDetails()
                        .stream()
                        .filter(bd -> !bd.isReturn() && availableBookIsbnList.contains(bd.getIsbn()))
                        .map(BorrowingDetail::getTitle)
                        .toList()));

        if (!sameIsbnBorrowedBookTitle.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Borrower with id %d has borrowed book with book title [%s].",
                            borrower.borrowerId(),
                            String.join(", ", sameIsbnBorrowedBookTitle)));
        }
    }
}
