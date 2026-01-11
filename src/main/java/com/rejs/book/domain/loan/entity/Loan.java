package com.rejs.book.domain.loan.entity;

import com.rejs.book.domain.holding.entity.Holding;
import com.rejs.book.domain.user.entity.User;
import com.rejs.book.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE loans SET deleted_at = NOW() WHERE loan_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "loans")
public class Loan extends BaseEntity {
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

    // 관계 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
