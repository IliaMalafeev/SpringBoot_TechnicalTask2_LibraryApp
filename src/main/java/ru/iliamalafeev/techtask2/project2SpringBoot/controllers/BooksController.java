package ru.iliamalafeev.techtask2.project2SpringBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Book;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Person;
import ru.iliamalafeev.techtask2.project2SpringBoot.services.BooksService;
import ru.iliamalafeev.techtask2.project2SpringBoot.services.PeopleService;
import ru.iliamalafeev.techtask2.project2SpringBoot.utils.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String showAllBooks(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                               @RequestParam(value = "sort_by_year", required = false) boolean sorted,
                               Model model) {
        if (page != null && booksPerPage != null) {
            model.addAttribute("books", booksService.findAll(PageRequest.of(page, booksPerPage), sorted));
        } else {
            model.addAttribute("books", booksService.findAll(sorted));
        }
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findById(id);
        Person holder = book.getHolder();
        model.addAttribute("book", book);
        if (holder != null) {
            model.addAttribute("holder", holder);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/book";
    }

    @GetMapping("/new")
    public String showNewBookForm(@ModelAttribute("book") Book book) {
        return "books/new_book_form";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new_book_form";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showBookUpdatePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit_book_form";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit_book_form";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    // We receive a person object with only field id set, just enough to use it as foreign key for the selected book
    @PatchMapping("/{id}/assign")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        booksService.assignBook(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/unbind")
    public String unbindBook(@PathVariable("id") int id) {
        booksService.unbindBook(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "books/search_for_book";
    }

    @PostMapping("search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.findByTitleStartingWith(query));
        return "books/search_for_book";
    }
}
