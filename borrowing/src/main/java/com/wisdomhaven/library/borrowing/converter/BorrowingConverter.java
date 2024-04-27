package com.wisdomhaven.library.borrowing.converter;

import com.wisdomhaven.library.borrowing.dto.apiResult.Book;
import com.wisdomhaven.library.borrowing.dto.response.BorrowingDetailResponseDTO;
import com.wisdomhaven.library.borrowing.model.BorrowingDetail;

public class BorrowingConverter {
    private BorrowingConverter() {}

    public static BorrowingDetail toBorrowingDetail(Book book) {
        return BorrowingDetail
                .builder()
                .bookId(book.bookId())
                .title(book.title())
                .isbn(book.isbn())
                .build();
    }

    public static BorrowingDetailResponseDTO toBorrowingDetailResponseDTO(BorrowingDetail borrowingDetail) {
        return BorrowingDetailResponseDTO
                .builder()
                .borrowingDetailId(borrowingDetail.getBorrowingDetailId())
                .bookId(borrowingDetail.getBookId())
                .title(borrowingDetail.getTitle())
                .isbn(borrowingDetail.getIsbn())
                .build();
    }
}
