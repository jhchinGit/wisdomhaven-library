package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BorrowerConverter;
import com.wisdomhaven.library.dto.response.BorrowerResponseDTO;
import com.wisdomhaven.library.model.Borrower;
import com.wisdomhaven.library.repository.BorrowerRepository;
import com.wisdomhaven.library.service.IBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BorrowerResponseDTO> getBorrowers(Integer id, String name, String email) {
        // TODO: Pagination, sorting, Hateoas
        List<BorrowerResponseDTO> borrowerResponseDTOList = this.borrowerRepository
                .findByIdOrNameOrEmail(id, name, email)
                .stream()
                .map(BorrowerConverter::ToBorrowerResponseDTO)
                .toList();

        if (borrowerResponseDTOList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return borrowerResponseDTOList;
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
