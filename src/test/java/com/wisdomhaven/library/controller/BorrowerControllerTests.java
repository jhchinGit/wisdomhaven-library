package com.wisdomhaven.library.controller;

import com.wisdomhaven.library.dto.request.*;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.dto.response.PageResponseDTO;
import com.wisdomhaven.library.service.IBorrowerService;
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
public class BorrowerControllerTests {
    @Mock
    private IBorrowerService borrowerService;
    @InjectMocks
    private BorrowerController borrowerController;
    private static final List<Sort.Order> defaultBookOrderByList = List.of(Sort.Order.asc("name"));
    private static final Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(defaultBookOrderByList));
    private BorrowerRequestCriteria getBorrowerRequestCriteria() {
        return new BorrowerRequestCriteria(Optional.empty(), Optional.empty(), Optional.empty());
    }

    private PageableRequest getPageableRequest() {
        return new PageableRequest(Optional.empty(), Optional.empty(), Optional.empty());
    }

    @Test
    void givenSearchCriteria_whenGetBorrowers_thenStatus200() {
        BorrowerResponseDTO borrowerResponseDTO  = BorrowerResponseDTO
                .builder()
                .borrowerId(1)
                .name("Tester 1")
                .email("tester.1@tester.com")
                .build();

        Page<BorrowerResponseDTO> borrowerResponseDTOPage = new PageImpl<>(List.of(borrowerResponseDTO), defaultPageable, 1);

        PageResponseDTO<BorrowerResponseDTO> pageResponseDTO = new PageResponseDTO<>(
                1,
                borrowerResponseDTOPage.getContent().size(),
                borrowerResponseDTOPage.getPageable().getPageNumber() + 1,
                borrowerResponseDTOPage.getPageable().getPageSize(),
                false,
                borrowerResponseDTOPage.getContent());

        Mockito.when(this.borrowerService.getBorrowers(any(), any(), any(), any(), any(), any()))
                .thenReturn(borrowerResponseDTOPage);

        ResponseEntity<PageResponseDTO<BorrowerResponseDTO>> result =
                this.borrowerController.getBorrowers(getBorrowerRequestCriteria(), getPageableRequest());
        assertEquals(result.getStatusCode().value(), HttpStatus.OK.value());
        assertEquals(pageResponseDTO.total(), Objects.requireNonNull(result.getBody()).total());
        assertEquals(pageResponseDTO.pageSize(), Objects.requireNonNull(result.getBody()).pageSize());
        assertEquals(pageResponseDTO.pageNumber(), Objects.requireNonNull(result.getBody()).pageNumber());
        assertEquals(pageResponseDTO.count(), Objects.requireNonNull(result.getBody()).count());
        assertEquals(pageResponseDTO.hasMore(), Objects.requireNonNull(result.getBody()).hasMore());
        assertEquals(pageResponseDTO.items(), Objects.requireNonNull(result.getBody()).items());
    }

    @Test
    void givenBorrowerRequestBody_whenCreateBorrower_thenStatus201() {
        String name = "Tester 1";
        String email = "tester.1@tester.com";
        BorrowerRequestBody borrowerRequestBody = new BorrowerRequestBody(name, email);
        BorrowerResponseDTO borrowerResponseDTO  = BorrowerResponseDTO
                .builder()
                .borrowerId(1)
                .name(name)
                .email(email)
                .build();

        Mockito.when(this.borrowerService.createBorrower(name, email)).thenReturn(borrowerResponseDTO);

        ResponseEntity<BorrowerResponseDTO> result = this.borrowerController.createBorrower(borrowerRequestBody);
        assertEquals(result.getStatusCode().value(), HttpStatus.CREATED.value());
        assertEquals(borrowerResponseDTO.borrowerId(), Objects.requireNonNull(result.getBody()).borrowerId());
        assertEquals(borrowerResponseDTO.name(), Objects.requireNonNull(result.getBody()).name());
        assertEquals(borrowerResponseDTO.email(), Objects.requireNonNull(result.getBody()).email());
    }
}
