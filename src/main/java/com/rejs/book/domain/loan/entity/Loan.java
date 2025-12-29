package com.rejs.book.domain.loan.entity;

import com.rejs.book.domain.holding.entity.Holding;
import com.rejs.book.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    @Column
    private LocalDateTime loanDate;

    @Column
    private LocalDateTime dueDate;

    @Column
    private LocalDateTime returnDate;

    @Column
    private boolean isExtended;

    @Enumerated(EnumType.STRING)
    @Column
    private LoanStatus status;

    // 관계 소장도서
    @ManyToOne(fetch = FetchType.LAZY)
    private Holding holding;

    public void mapHolding(Holding holding){
        this.holding = holding;
        this.holding.getLoans().add(this);
    }

    // 관계 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void mapUser(User user){
        this.user = user;
        this.user.getLoans().add(this);
    }
}
