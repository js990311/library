package com.rejs.book.domain.holding.entity;

import com.rejs.book.domain.book.entity.Book;
import com.rejs.book.domain.library.entity.Library;
import com.rejs.book.domain.loan.entity.Loan;
import com.rejs.book.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holidng_id")
    private Long id;

    @Column
    private String callNumber;

    @Column
    private String room;

    @Enumerated(EnumType.STRING)
    @Column
    private HoldingStatus status;

    // 관계 - 책
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void mapBook(Book book){
        this.book = book;
        this.book.getHoldings().add(this);
    }

    // 관계 도서관
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    public void mapLibrary(Library library){
        this.library = library;
        this.library.getHoldings().add(this);
    }

    @OneToMany(mappedBy = "holding")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "holding")
    private List<Loan> loans = new ArrayList<>();

}
