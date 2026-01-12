package com.rejs.book.domain.library.repository;

import com.rejs.book.domain.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
