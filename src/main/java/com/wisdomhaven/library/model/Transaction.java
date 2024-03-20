package com.wisdomhaven.library.model;

import java.time.LocalDate;

public class Transaction {
    private int bookId;
    private int borrowerId;
    private LocalDate transactionDate;
    private boolean isReturn;

    public Transaction() {
    }

    public Transaction(int bookId, int borrowerId, LocalDate transactionDate, boolean isReturn) {
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.transactionDate = transactionDate;
        this.isReturn = isReturn;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
