package com.rejs.book.domain.reservation.entity;

import com.rejs.book.domain.holding.entity.Holding;
import com.rejs.book.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Reservation {

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

    public void mapHolding(Holding holding){
        this.holding = holding;
        this.holding.getReservations().add(this);
    }

    // 관계 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void mapUser(User user){
        this.user = user;
        this.user.getReservations().add(this);
    }

}
