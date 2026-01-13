package com.rejs.book.domain.book.controller;

import com.rejs.book.domain.book.dto.BookDto;
import com.rejs.book.domain.book.dto.CreateBookRequest;
import com.rejs.book.domain.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {
    private final BookService bookService;

    @GetMapping
    public String getIndex(
            @PageableDefault(size = 30)Pageable pageable,
            Model model
    ){
        Page<BookDto> bookPage = bookService.readByPage(pageable);
        model.addAttribute("bookPage", bookPage);
        return "book/index";
    }

    @GetMapping("/{id}")
    public String getBookById(
            @PathVariable("id") Long bookId, Model model
    ){
        BookDto book = bookService.readById(bookId);
        model.addAttribute("book", book);
        return "book/id";
    }

    @GetMapping("/create")
    public String getBookCreate(Model model){
        model.addAttribute("createBookRequest", new CreateBookRequest());
        return "book/create";
    }

    @PostMapping("/create")
    public String postBookCreate(@Valid @ModelAttribute("createBookRequest") CreateBookRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/create";
        }else {
            Long bookId = bookService.createBook(request);
            return "redirect:/books/" + bookId;
        }
    }
}
