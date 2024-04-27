package com.wisdomhaven.library.borrowing.model;

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
@Table(name = "borrowing_detail")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_detail_id")
    private Integer borrowingDetailId;

    @Column(name = "book_id")
    @NotNull
    private Integer bookId;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "isbn")
    @NotNull
    private String isbn;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "borrowingId")
    private Borrowing borrowing;

    @Column(name = "created_date")
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp(source = SourceType.DB)
    private Instant modifiedDate;
}
