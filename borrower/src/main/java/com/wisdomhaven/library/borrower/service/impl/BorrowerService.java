package com.wisdomhaven.library.borrower.service.impl;

import com.wisdomhaven.library.borrower.constant.ErrorMessageConstants;
import com.wisdomhaven.library.borrower.converter.BorrowerConverter;
import com.wisdomhaven.library.borrower.dto.misc.Sortable;
import com.wisdomhaven.library.borrower.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.borrower.model.Borrower;
import com.wisdomhaven.library.borrower.repository.BorrowerRepository;
import com.wisdomhaven.library.borrower.service.IBorrowerService;
import com.wisdomhaven.library.borrower.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowerService implements IBorrowerService {

    private final BorrowerRepository borrowerRepository;
    private static final Map<String, String> borrowerValidOrderByFieldMap;
    private static final List<Sort.Order> defaultBorrowOrderByList = List.of(Sort.Order.asc("name"));

    static {
        borrowerValidOrderByFieldMap = new HashMap<>();
        borrowerValidOrderByFieldMap.put("name", "name");
        borrowerValidOrderByFieldMap.put("email", "email");
    }

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public Page<BorrowerResponseDTO> getBorrowers(Integer borrowerId,
                                                  String name,
                                                  String email,
                                                  Integer pageNumber,
                                                  Integer pageSize,
                                                  String orderBy) {
        Sortable sortable = PageableUtil.getSortable(orderBy, borrowerValidOrderByFieldMap, defaultBorrowOrderByList);
        if (!sortable.isValid() || sortable.sort() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageConstants.INVALID_ORDER_BY);
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortable.sort());

        Page<Borrower> borrowerPage = this.borrowerRepository.findByBorrowerIdOrNameOrEmail(borrowerId, name, email, pageable);
        if (borrowerPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        List<BorrowerResponseDTO> borrowerResponseDTOList = borrowerPage
                .map(BorrowerConverter::toBorrowerResponseDTO)
                .toList();

        return new PageImpl<>(borrowerResponseDTOList, pageable, borrowerPage.getTotalElements());
    }

    @Override
    public BorrowerResponseDTO getBorrower(Integer borrowerId) {
        return this.borrowerRepository.findById(borrowerId)
                .map(BorrowerConverter::toBorrowerResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Borrower id %d not found.", borrowerId)));
    }

    @Override
    public BorrowerResponseDTO createBorrower(String name, String email) {
        if (this.borrowerRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Borrower email %s is used.", email));
        }

        return BorrowerConverter.toBorrowerResponseDTO(
                this.borrowerRepository.save(
                        Borrower.builder()
                                .name(name)
                                .email(email)
                                .build()));
    }
}
