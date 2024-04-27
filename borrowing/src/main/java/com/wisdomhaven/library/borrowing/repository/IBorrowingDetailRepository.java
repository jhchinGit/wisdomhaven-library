package com.wisdomhaven.library.borrowing.repository;

import com.wisdomhaven.library.borrowing.model.BorrowingDetail;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface IBorrowingDetailRepository extends ListCrudRepository<BorrowingDetail, Integer> {
    Optional<BorrowingDetail> findByBorrowingBorrowingIdAndBookId(Integer borrowingId, Integer bookId);
}
