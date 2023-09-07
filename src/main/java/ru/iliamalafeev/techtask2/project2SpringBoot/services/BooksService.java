package ru.iliamalafeev.techtask2.project2SpringBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Book;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Person;
import ru.iliamalafeev.techtask2.project2SpringBoot.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sorted) {
        if (sorted) return booksRepository.findAll(Sort.by("yearOfPublication"));
        else return booksRepository.findAll();
    }

    public List<Book> findAll(Pageable pageable, boolean sorted) {
        if (sorted) return booksRepository
                .findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("yearOfPublication")))
                .getContent();
        else return booksRepository
                .findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted()))
                .getContent();
    }

    // This method is used in PeopleController in showPerson method to pass a list of books owned by a person, if any
    public List<Book> findAllByHolder(Person holder) {
        List<Book> books = booksRepository.findAllByHolder(holder);
        for (Book book : books) {
            if (System.currentTimeMillis() - book.getTakenAt().getTime() >= 864_000_000) book.setExpired(true);
        }
        return booksRepository.findAllByHolder(holder);
    }

    public List<Book> findByTitleStartingWith(String titleBeginning) {
        return booksRepository.findByTitleStartingWith(titleBeginning);
    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToUpdate = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setHolder(bookToUpdate.getHolder());
        updatedBook.setTakenAt(bookToUpdate.getTakenAt());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    // This method is used in BookController to set Person.id value as foreign key for the selected book
    // This method is called from the book page when selected book is getting "assigned" to some person
    @Transactional
    public void assignBook(int bookId, Person person) {
        Book book = booksRepository.findById(bookId).get();
        book.setHolder(person);
        book.setTakenAt(new Date());
        book.setExpired(false);
    }

    // This method is used in BookController to set book's foreign key value as null
    // This method is called from the book page when selected book is getting set as "free to take"
    @Transactional
    public void unbindBook(int bookId) {
        Book book = booksRepository.findById(bookId).get();
        book.setHolder(null);
        book.setTakenAt(null);
        book.setExpired(false);
    }
}
