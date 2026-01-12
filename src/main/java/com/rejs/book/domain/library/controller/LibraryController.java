package com.rejs.book.domain.library.controller;

import com.rejs.book.domain.library.dto.LibraryRequest;
import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.service.LibraryService;
import com.rejs.book.global.security.dto.LoginRequest;
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
@RequestMapping("/libraries")
@Controller
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public String getList(
            @PageableDefault(size = 15) Pageable pageable,
            Model model
    ){
        Page<LibraryResponse> libraryPage = libraryService.readAllByPage(pageable);
        model.addAttribute("libraryPage", libraryPage);
        return "library/index";
    }

    @GetMapping("/create")
    public String getLibraryCreate(Model model){
        model.addAttribute("libraryRequest", new LibraryRequest());
        return "library/create";
    }

    @PostMapping("/create")
    public String postLibraryCreate(
            @Valid @ModelAttribute("libraryRequest") LibraryRequest libraryRequest,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "library/create";
        }
        libraryService.create(libraryRequest);
        return "redirect:/libraries";
    }

    @GetMapping("/{id}")
    public String getLibraryId(
            @PathVariable("id") Long libraryId,
            Model model
    ){
        LibraryResponse library = libraryService.readById(libraryId);
        model.addAttribute("library", library);
        return "library/id";
    }

    @PostMapping("/{id}/delete")
    public String postLibraryDelete(@PathVariable("id") Long libraryId){
        libraryService.delete(libraryId);
        return "redirect:/libraries";
    }

}
