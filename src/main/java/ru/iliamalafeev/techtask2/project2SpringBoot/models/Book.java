package ru.iliamalafeev.techtask2.project2SpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Cannot be left empty")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Cannot be left empty")
    @Column(name = "author")
    private String author;
    @Column(name = "year_of_publication")
    private int yearOfPublication;
    @ManyToOne
    @JoinColumn(name = "holder_id", referencedColumnName = "id")
    private Person holder;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String title, String author, int year_of_publication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = year_of_publication;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYearOfPublication() {
        return yearOfPublication;
    }
    public void setYearOfPublication(int year_of_publication) {
        this.yearOfPublication = year_of_publication;
    }
    public Person getHolder() {
        return holder;
    }
    public void setHolder(Person holder) {
        this.holder = holder;
    }
    public Date getTakenAt() {
        return takenAt;
    }
    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
    public boolean isExpired() {
        return expired;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
