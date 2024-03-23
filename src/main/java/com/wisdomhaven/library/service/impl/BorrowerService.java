package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.constant.ErrorMessageConstants;
import com.wisdomhaven.library.converter.BorrowerConverter;
import com.wisdomhaven.library.dto.misc.Sortable;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.model.Borrower;
import com.wisdomhaven.library.repository.BorrowerRepository;
import com.wisdomhaven.library.service.IBorrowerService;
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
    public Page<BorrowerResponseDTO> getBorrowers(Integer id,
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

        Page<Borrower> borrowerPage = this.borrowerRepository.findByIdOrNameOrEmail(id, name, email, pageable);
        if (borrowerPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        List<BorrowerResponseDTO> borrowerResponseDTOList = borrowerPage
                .map(BorrowerConverter::ToBorrowerResponseDTO)
                .toList();

        return new PageImpl<>(borrowerResponseDTOList, pageable, borrowerPage.getTotalElements());
    }

    @Override
    public BorrowerResponseDTO createBorrower(String name, String email) {
        if (this.borrowerRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Borrower email %s is used.", email));
        }

        return BorrowerConverter.ToBorrowerResponseDTO(
                this.borrowerRepository.save(
                        Borrower.builder()
                                .name(name)
                                .email(email)
                                .build()));
    }
}
