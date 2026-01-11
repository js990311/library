package com.rejs.book.domain.reservation.entity;

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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE reservations SET deleted_at = NOW() WHERE reservation_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column
    private ReservationStatus status;

    // 관계 소장도서
    @ManyToOne(fetch = FetchType.LAZY)
    private Holding holding;

    // 관계 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
