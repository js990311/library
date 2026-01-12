package com.rejs.book.domain.library.service;

import com.rejs.book.domain.library.dto.LibraryRequest;
import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.entity.Library;
import com.rejs.book.domain.library.exception.LibraryErrorCode;
import com.rejs.book.domain.library.exception.LibraryException;
import com.rejs.book.domain.library.exception.LibraryNotFoundException;
import com.rejs.book.domain.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    // CREATE

    @Transactional
    public Long create(LibraryRequest request){
        Library library = Library.builder()
                .name(request.getName())
                .location(request.getLocation())
                .webpage(request.getWebpage())
                .build();
        return libraryRepository.save(library).getId();
    }

    // READ

    @Transactional(readOnly = true)
    public Page<LibraryResponse> readAllByPage(Pageable pageable){
        return libraryRepository.findAll(pageable)
                .map(LibraryResponse::from);
    }

    @Transactional(readOnly = true)
    public LibraryResponse readById(Long libraryId){
        Library library = libraryRepository.findById(libraryId).orElseThrow(LibraryNotFoundException::new);
        return LibraryResponse.from(library);
    }

    // UPDATE
    @Transactional
    public void update(Long libraryId, LibraryRequest request){
        Library library = libraryRepository.findById(libraryId).orElseThrow(LibraryNotFoundException::new);
        library.update(
                request.getName(),
                request.getLocation(),
                request.getWebpage()
        );
    }

    // DELETE
    @Transactional
    public void delete(Long libraryId){
        if(!libraryRepository.existsById(libraryId)){
            throw new LibraryNotFoundException();
        }
        libraryRepository.deleteById(libraryId);
    }
}
