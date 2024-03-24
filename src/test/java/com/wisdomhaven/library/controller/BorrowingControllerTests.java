package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.BorrowingRequestBody;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.dto.response.BorrowingResponseDTO;
import com.wisdomhaven.library.service.IBorrowingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BorrowingControllerTests {
    @Mock
    private IBorrowingService borrowingService;
    @InjectMocks
    private BorrowingController borrowingController;

    private List<BookResponseDTO> getBookResponseDTOList() {
        return List.of(
                BookResponseDTO.builder().bookId(1).title("The Book 1").author("Book Author 1").isbn("1").isAvailable(true).build(),
                BookResponseDTO.builder().bookId(2).title("The Book 2").author("Book Author 2").isbn("2").isAvailable(true).build()
        );
    }

    @Test
    void givenBorrowingRequestBody_whenCreateBorrowing_thenStatus201() {
        Integer borrowerId = 1;
        List<Integer> bookIdList = List.of(1, 2);
        BorrowingRequestBody borrowingRequestBody = new BorrowingRequestBody(borrowerId, bookIdList);
        BorrowingResponseDTO borrowingResponseDTO  = BorrowingResponseDTO
                .builder()
                .transactionId(1)
                .bookResponseDTOList(getBookResponseDTOList())
                .build();

        Mockito.when(this.borrowingService.createBorrowing(borrowerId, bookIdList)).thenReturn(borrowingResponseDTO);

        ResponseEntity<BorrowingResponseDTO> result = this.borrowingController.createBorrowing(borrowingRequestBody);
        assertEquals(result.getStatusCode().value(), HttpStatus.CREATED.value());
        assertEquals(borrowingResponseDTO.transactionId(), Objects.requireNonNull(result.getBody()).transactionId());
        assertEquals(borrowingResponseDTO.bookResponseDTOList(), Objects.requireNonNull(result.getBody()).bookResponseDTOList());
    }

    @Test
    void givenTransactionId_whenReturnAllBooks_thenStatus200() {
        Integer transactionId = 1;
        String expectedMessage = "All books have been returned.";

        Mockito.when(this.borrowingService.returnAllBooks(transactionId)).thenReturn(expectedMessage);

        ResponseEntity<String> result = this.borrowingController.returnAllBooks(transactionId);
        assertEquals(result.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(expectedMessage, Objects.requireNonNull(result.getBody()));
    }

    @Test
    void givenTransactionIdAndBookId_whenReturnBookByBookId_thenStatus200() {
        Integer transactionId = 1;
        Integer bookId = 1;
        String expectedMessage = String.format("Book %d is returned.", bookId);

        Mockito.when(this.borrowingService.returnBookByBookId(transactionId, bookId))
                .thenReturn(expectedMessage);

        ResponseEntity<String> result = this.borrowingController.returnBookByBookId(transactionId, bookId);
        assertEquals(result.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(expectedMessage, Objects.requireNonNull(result.getBody()));
    }
}
