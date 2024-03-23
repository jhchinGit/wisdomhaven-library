package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BorrowerConverter;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.model.Borrower;
import com.wisdomhaven.library.repository.BorrowerRepository;
import com.wisdomhaven.library.service.IBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BorrowerService implements IBorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final String USED_BORROWER_EMAIL = "Borrower email %s is used.";

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public Page<BorrowerResponseDTO> getBorrowers(Integer id,
                                                  String name,
                                                  String email,
                                                  Integer pageNumber,
                                                  Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        // TODO: sorting, Hateoas
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(USED_BORROWER_EMAIL, email));
        }

        return BorrowerConverter.ToBorrowerResponseDTO(
                this.borrowerRepository.save(
                        Borrower.builder()
                                .name(name)
                                .email(email)
                                .build()));
    }
}
