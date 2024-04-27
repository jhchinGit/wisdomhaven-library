package com.wisdomhaven.library.borrowing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "borrowing")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_id")
    private int borrowingId;

    @Column(name = "borrower_id")
    @NotNull
    private int borrowerId;

    @Column(name = "borrower_name")
    @NotNull
    private String borrowerName;

    @Column(name = "is_fully_return")
    @NotNull
    private boolean isFullyReturn;

    @OneToMany(mappedBy = "borrowing", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @NotEmpty
    private List<BorrowingDetail> borrowingDetails;

    @Column(name = "created_date")
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp(source = SourceType.DB)
    private Instant modifiedDate;
}
