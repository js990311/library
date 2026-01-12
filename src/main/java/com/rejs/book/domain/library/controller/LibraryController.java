package com.rejs.book.domain.library.controller;

import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
