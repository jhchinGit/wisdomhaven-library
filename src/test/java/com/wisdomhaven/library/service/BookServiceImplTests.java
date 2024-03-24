package com.wisdomhaven.library.service;

import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.service.impl.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceImplTests {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    private static final List<Sort.Order> defaultBookOrderByList = List.of(Sort.Order.asc("title"));
    private static final Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(defaultBookOrderByList));

    @Test
    void givenBookId_whenGetBooks_thenReturnBookResponseDTOPage() {
        Integer bookId = 1;
        String title = "The Book";
        String author = "Book Author";
        String isbn = "1";
        Book book = Book.builder().bookId(bookId).title(title).author(author).isbn(isbn).build();
        Page<Book> bookPage = new PageImpl<>(List.of(book), defaultPageable, 1);
        BookResponseDTO bookResponseDTO  = BookResponseDTO
                .builder()
                .bookId(bookId)
                .title(title)
                .author(author)
                .isbn(isbn)
                .isAvailable(true)
                .build();
        Page<BookResponseDTO> bookResponseDTOPage = new PageImpl<>(List.of(bookResponseDTO), defaultPageable, 1);

        Mockito.when(this.bookRepository.findByBookIdOrTitleOrAuthorOrIsbn(bookId, null, null, null, defaultPageable))
                .thenReturn(bookPage);

        Page<BookResponseDTO> result =
                this.bookService.getBooks(bookId, null, null, null, 0, 10, null);
        assertEquals(bookResponseDTOPage, result);
    }

    @Test
    void givenInvalidOrderBy_whenGetBooks_thenThrowResponseStatusException() {
        assertThrows(ResponseStatusException.class,
                () -> this.bookService.getBooks(null, null, null, null, 0, 10, "invalid order by"));
    }

    @Test
    void givenNoneExistsBookId_whenGetBooks_thenThrowResponseStatusException() {
        Mockito.when(this.bookRepository.findByBookIdOrTitleOrAuthorOrIsbn(null, null, null, null, defaultPageable))
                .thenReturn(Page.empty());

        assertThrows(ResponseStatusException.class,
                () -> this.bookService.getBooks(null, null, null, null, 0, 10, null));
    }

    @Test
    void givenTitleAndAuthorAndIsbn_whenCreateBook_thenReturnBookResponseDTO() {
        Integer bookId = 1;
        String title = "The Book";
        String author = "Book Author";
        String isbn = "1";
        Book book = Book.builder().bookId(bookId).title(title).author(author).isbn(isbn).build();
        BookResponseDTO bookResponseDTO  = BookResponseDTO
                .builder()
                .bookId(bookId)
                .title(title)
                .author(author)
                .isbn(isbn)
                .isAvailable(true)
                .build();

        Mockito.when(this.bookRepository.findByIsbn(isbn)).thenReturn(new ArrayList<>());
        Mockito.when(this.bookRepository.save(any())).thenReturn(book);

        BookResponseDTO result = this.bookService.createBook(title, author, isbn);
        assertEquals(bookResponseDTO, result);
    }

    @Test
    void givenDifferentTitleAndSameAuthorAndSameIsbn_whenCreateBook_thenReturnBookResponseDTO() {
        String title = "The Book";
        String author = "Book Author";
        String isbn = "1";
        Book differentBook = Book.builder().bookId(1).title("different title").author(author).isbn(isbn).build();

        Mockito.when(this.bookRepository.findByIsbn(isbn)).thenReturn(List.of(differentBook));

        assertThrows(ResponseStatusException.class, () -> this.bookService.createBook(title, author, isbn));
    }

    @Test
    void givenSameTitleAndDifferentAuthorAndSameIsbn_whenCreateBook_thenReturnBookResponseDTO() {
        String title = "The Book";
        String author = "Book Author";
        String isbn = "1";
        Book differentBook = Book.builder().bookId(1).title(title).author("different author").isbn(isbn).build();

        Mockito.when(this.bookRepository.findByIsbn(isbn)).thenReturn(List.of(differentBook));

        assertThrows(ResponseStatusException.class, () -> this.bookService.createBook(title, author, isbn));
    }
}
