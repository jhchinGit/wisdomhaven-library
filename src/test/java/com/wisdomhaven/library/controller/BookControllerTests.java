package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.service.IBookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BookControllerTests {
    @Mock
    private IBookService bookService;
    @InjectMocks
    private BookController bookController ;

    @Test
    void givenBookIds_whenGetBook_thenStatus200() {
        Mockito.when(this.bookService.getBook(1)).thenReturn(new BookResponseDTO(1, "a", "a", "1"));

        ResponseEntity<BookResponseDTO> result = this.bookController.getBook(1);
        assertEquals(result.getStatusCode().value(), HttpStatus.OK.value());
    }
}
