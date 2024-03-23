package com.wisdomhaven.library.model;

import jakarta.persistence.*;
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
@Table(name = "transaction")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "is_return")
    private boolean isReturn;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Book> books;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "borrowerId")
    private Borrower borrower;

    @Column(name = "created_date")
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp(source = SourceType.DB)
    private Instant modifiedDate;
}
