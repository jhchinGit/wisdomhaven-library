package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.constant.ErrorMessageConstants;
import com.wisdomhaven.library.converter.BookConverter;
import com.wisdomhaven.library.dto.misc.Sortable;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.service.IBookService;
import com.wisdomhaven.library.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private static final Map<String, String> bookValidOrderByFieldMap;
    private static final List<Sort.Order> defaultBookOrderByList = List.of(Sort.Order.asc("title"));
    private static final String UNMATCHED_BOOK_TITLE_OR_AUTHOR =
            "The ISBN %s corresponds to an existing book, but either the title or the author does not match.";

    static {
        bookValidOrderByFieldMap = new HashMap<>();
        bookValidOrderByFieldMap.put("title", "title");
        bookValidOrderByFieldMap.put("author", "author");
        bookValidOrderByFieldMap.put("isbn", "isbn");
    }

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<BookResponseDTO> getBooks(Integer bookId,
                                          String title,
                                          String author,
                                          String isbn,
                                          Integer pageNumber,
                                          Integer pageSize,
                                          String orderBy) {
        Sortable sortable = PageableUtil.getSortable(orderBy, bookValidOrderByFieldMap, defaultBookOrderByList);
        if (!sortable.isValid() || sortable.sort() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageConstants.INVALID_ORDER_BY);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable.sort());

        Page<Book> bookPage = this.bookRepository.findByBookIdOrTitleOrAuthorOrIsbn(bookId, title, author, isbn, pageable);
        if (bookPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        List<BookResponseDTO> bookResponseDTOList = bookPage
                .map(BookConverter::toBookResponseDTO)
                .stream().toList();

        return new PageImpl<>(bookResponseDTOList, pageable, bookPage.getTotalElements());
    }

    @Override
    public BookResponseDTO getBook(Integer bookId) {
        return this.bookRepository.findById(bookId)
                .map(BookConverter::toBookResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Book id %d not found.", bookId)));
    }

    @Override
    public BookResponseDTO createBook(String title, String author, String isbn) {
        List<Book> existingBooks = this.bookRepository.findByIsbn(isbn);
        validateBookCreation(existingBooks, title, author, isbn);

        return BookConverter.toBookResponseDTO(
                this.bookRepository.save(
                                Book.builder()
                                        .title(title)
                                        .author(author)
                                        .isbn(isbn)
                                        .build()));
    }

    private void validateBookCreation(List<Book> existingBooks, String title, String author, String isbn) {
        boolean isSameIsbnButUnmatchedTitleOrAuthor =
                !existingBooks.isEmpty() &&
                existingBooks
                        .stream()
                        .noneMatch(b ->
                                b.getTitle().equals(title) &&
                                b.getAuthor().equals(author));

        if (isSameIsbnButUnmatchedTitleOrAuthor) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(UNMATCHED_BOOK_TITLE_OR_AUTHOR, isbn));
        }
    }
}
