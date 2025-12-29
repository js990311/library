package com.rejs.book.domain.book.entity;

import com.rejs.book.domain.holding.entity.Holding;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String isbn;

    // 관계 - 소장도서

    @OneToMany(mappedBy = "book")
    private List<Holding> holdings = new ArrayList<>();
}
