package ru.iliamalafeev.techtask2.project2SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Book;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByHolder(Person holder);
    List<Book> findByTitleStartingWith(String title);
}
