package com.wisdomhaven.library.service.impl;

import com.wisdomhaven.library.converter.BookConverter;
import com.wisdomhaven.library.dto.response.BookResponseDTO;
import com.wisdomhaven.library.model.Book;
import com.wisdomhaven.library.repository.BookRepository;
import com.wisdomhaven.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private static final String BOOK_NOT_FOUND = "The book id %d cannot be found.";
    private static final String UNMATCHED_BOOK_TITLE_OR_AUTHOR =
            "The ISBN %s corresponds to an existing book, but either the title or the author does not match.";

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponseDTO> getBooks(Integer id, String title, String author, String isbn) {
        // TODO: Pagination, Hateoas
        List<BookResponseDTO> bookResponseDTOList = this.bookRepository
                .findByIdOrTitleOrAuthorOrIsbn(id, title, author, isbn)
                .stream()
                .map(BookConverter::ToBookResponseDTO)
                .toList();

        if (bookResponseDTOList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return bookResponseDTOList;
    }

    @Override
    public BookResponseDTO getBook(Integer id) {
        return this.bookRepository
                .findById(id)
                .map(BookConverter::ToBookResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format(BOOK_NOT_FOUND, id)));
    }

    @Override
    public BookResponseDTO createBook(String title, String author, String isbn) {
        List<Book> existingBooks = this.bookRepository.findByIsbn(isbn).stream().toList();
        validateBookCreation(existingBooks, title, author, isbn);

        return BookConverter.ToBookResponseDTO(
                this.bookRepository.save(
                                Book.builder()
                                        .title(title)
                                        .author(author)
                                        .isbn(isbn)
                                        .quantity(existingBooks.size() + 1)
                                        .build()));
    }

    private void validateBookCreation(List<Book> existingBooks, String title, String author, String isbn) {
        boolean isSameIsbnButUnmatchedTitleOrAuthor =
                !existingBooks.isEmpty() &&
                existingBooks
                        .stream()
                        .noneMatch(b ->
                                b.getTitle().equals(title) &&
                                b.getAuthor().equals(author));

        if (isSameIsbnButUnmatchedTitleOrAuthor) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(UNMATCHED_BOOK_TITLE_OR_AUTHOR, isbn));
        }
    }
}
