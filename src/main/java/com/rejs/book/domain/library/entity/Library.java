package com.rejs.book.domain.library.entity;

import com.rejs.book.domain.holding.entity.Holding;
import com.rejs.book.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@SQLDelete(sql = "UPDATE libraries SET deleted_at = NOW() WHERE library_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "libraries")
public class Library extends BaseEntity {
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
