package com.rejs.book.domain.holding.entity;

import com.rejs.book.domain.book.entity.Book;
import com.rejs.book.domain.library.entity.Library;
import com.rejs.book.domain.loan.entity.Loan;
import com.rejs.book.domain.reservation.entity.Reservation;
import com.rejs.book.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE holdings SET deleted_at = NOW() WHERE holding_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "holdings")
public class Holding extends BaseEntity {
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

    // 관계 도서관
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;
}
