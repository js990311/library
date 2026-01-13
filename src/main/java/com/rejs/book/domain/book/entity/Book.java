package com.rejs.book.domain.book.entity;

import com.rejs.book.domain.book.dto.UpdateBookRequest;
import com.rejs.book.domain.holding.entity.Holding;
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
@SQLDelete(sql = "UPDATE books SET deleted_at = NOW() WHERE book_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "books")
public class Book extends BaseEntity {
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

    public void update(UpdateBookRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.isbn = request.getIsbn();
    }
}
