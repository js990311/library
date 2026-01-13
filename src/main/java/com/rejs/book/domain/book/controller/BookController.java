package com.rejs.book.domain.book.controller;

import com.rejs.book.domain.book.dto.BookDto;
import com.rejs.book.domain.book.dto.CreateBookRequest;
import com.rejs.book.domain.book.dto.UpdateBookRequest;
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

    @PostMapping("/{id}/delete")
    public String postBookDelete(
            @PathVariable("id") Long bookId
    ){
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }

    @GetMapping("/{id}/update")
    public String getBookUpdate(
            @PathVariable("id") Long bookId,
            Model model
    ){
        BookDto bookDto = bookService.readById(bookId);
        UpdateBookRequest request = UpdateBookRequest.from(bookDto);
        model.addAttribute("updateBookRequest", request);
        return "book/update";
    }


    @PostMapping("/{id}/update")
    public String postBookUpdate(
            @PathVariable("id") Long bookId,
            @Valid @ModelAttribute("updateBookRequest") UpdateBookRequest request,
            BindingResult bindingResult

    ){
        if(bindingResult.hasErrors()){
            return "book/update";
        }
        bookService.update(bookId, request);
        return "redirect:/books/" + bookId;
    }

}
