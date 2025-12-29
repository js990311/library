package com.rejs.book.domain.library.entity;

import com.rejs.book.domain.holding.entity.Holding;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String webpage;

    // 관계 - 소장도서
    @OneToMany(mappedBy = "library")
    private List<Holding> holdings;
}
