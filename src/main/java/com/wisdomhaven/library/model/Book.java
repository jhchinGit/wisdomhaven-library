package com.wisdomhaven.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "book")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "author")
    @NotNull
    private String author;

    @Column(name = "isbn")
    @NotNull
    private String isbn;

    @Column(name = "created_date")
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp(source = SourceType.DB)
    private Instant modifiedDate;
}
