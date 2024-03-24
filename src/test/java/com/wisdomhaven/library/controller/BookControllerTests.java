package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BookRequestBody;
import com.wisdomhaven.library.dto.request.BookRequestCriteria;
import com.wisdomhaven.library.dto.request.PageableRequest;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.dto.response.PageResponseDTO;
import com.wisdomhaven.library.service.IBookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class BookControllerTests {
    @Mock
    private IBookService bookService;
    @InjectMocks
    private BookController bookController;
    private static final List<Sort.Order> defaultBookOrderByList = List.of(Sort.Order.asc("title"));
    private static final Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(defaultBookOrderByList));

    private BookRequestCriteria getBookRequestCriteria() {
        return new BookRequestCriteria(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    private PageableRequest getPageableRequest() {
        return new PageableRequest(Optional.empty(), Optional.empty(), Optional.empty());
    }

    @Test
    void givenSearchCriteria_whenGetBooks_thenStatus200() {
        BookResponseDTO bookResponseDTO  = BookResponseDTO
                .builder()
                .bookId(1)
                .title("The Book")
                .author("Book Author")
                .isbn("1")
                .isAvailable(true)
                .build();

        Page<BookResponseDTO> bookResponseDTOPage = new PageImpl<>(List.of(bookResponseDTO), defaultPageable, 1);

        PageResponseDTO<BookResponseDTO> pageResponseDTO = new PageResponseDTO<>(
                1,
                bookResponseDTOPage.getContent().size(),
                bookResponseDTOPage.getPageable().getPageNumber() + 1,
                bookResponseDTOPage.getPageable().getPageSize(),
                false,
                bookResponseDTOPage.getContent());

        Mockito.when(this.bookService.getBooks(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(bookResponseDTOPage);

        ResponseEntity<PageResponseDTO<BookResponseDTO>> result =
                this.bookController.getBooks(getBookRequestCriteria(), getPageableRequest());
        assertEquals(result.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(pageResponseDTO.total(), Objects.requireNonNull(result.getBody()).total());
        assertEquals(pageResponseDTO.pageSize(), Objects.requireNonNull(result.getBody()).pageSize());
        assertEquals(pageResponseDTO.pageNumber(), Objects.requireNonNull(result.getBody()).pageNumber());
        assertEquals(pageResponseDTO.count(), Objects.requireNonNull(result.getBody()).count());
        assertEquals(pageResponseDTO.hasMore(), Objects.requireNonNull(result.getBody()).hasMore());
        assertEquals(pageResponseDTO.items(), Objects.requireNonNull(result.getBody()).items());
    }

    @Test
    void givenBookRequestBody_whenCreateBook_thenStatus201() {
        String title = "The Book";
        String author = "Book Author";
        String isbn = "0123456789";
        BookRequestBody bookRequestBody = new BookRequestBody(title, author, isbn);
        BookResponseDTO bookResponseDTO  = BookResponseDTO
                .builder()
                .bookId(1)
                .title(title)
                .author(author)
                .isbn(isbn)
                .isAvailable(true)
                .build();

        Mockito.when(this.bookService.createBook(title, author, isbn)).thenReturn(bookResponseDTO);

        ResponseEntity<BookResponseDTO> result = this.bookController.createBook(bookRequestBody);
        assertEquals(result.getStatusCode().value(), HttpStatus.CREATED.value());
        assertEquals(bookResponseDTO.bookId(), Objects.requireNonNull(result.getBody()).bookId());
        assertEquals(bookResponseDTO.title(), Objects.requireNonNull(result.getBody()).title());
        assertEquals(bookResponseDTO.author(), Objects.requireNonNull(result.getBody()).author());
        assertEquals(bookResponseDTO.isbn(), Objects.requireNonNull(result.getBody()).isbn());
        assertEquals(bookResponseDTO.isAvailable(), Objects.requireNonNull(result.getBody()).isAvailable());
    }
}
