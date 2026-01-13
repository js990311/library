package com.rejs.book.domain.book.service;

import com.rejs.book.domain.book.dto.BookDto;
import com.rejs.book.domain.book.dto.CreateBookRequest;
import com.rejs.book.domain.book.dto.UpdateBookRequest;
import com.rejs.book.domain.book.entity.Book;
import com.rejs.book.domain.book.exception.BookNotFoundException;
import com.rejs.book.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    // Create
    @Transactional
    public Long createBook(CreateBookRequest request){
        Book book = Book.builder()
                .name(request.getName())
                .isbn(request.getIsbn())
                .description(request.getDescription())
                .build();
        return bookRepository.save(book).getId();
    }

    // Read
    @Transactional(readOnly = true)
    public Page<BookDto> readByPage(Pageable pageable){
        return bookRepository.findAll(pageable).map(BookDto::from);
    }

    @Transactional(readOnly = true)
    public BookDto readById(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return BookDto.from(book);
    }

    // Update
    @Transactional
    public void update(Long bookId, UpdateBookRequest request){
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        book.update(request);
    }

    // Delete
    @Transactional
    public void deleteBook(Long bookId){
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException();
        }
    }
}
