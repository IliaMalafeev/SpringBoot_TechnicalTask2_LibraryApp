# LibraryApp

Project is done as a technical task example.

Stack: Java, Spring Core, Spring MVC, Spring Boot, Spring Data JPA, 
Hibernate, Spring Validator, PostgreSQL, Thymeleaf.

## Technical Task

Rewrite [**TechnicalTask1_LibraryApp**](https://github.com/IliaMalafeev/Spring_TechnicalTask1_LibraryApp)
by implementing **Hibernate** and **Spring Data JPA**. Project now must not contain any SQL commands.
Entities `Book` and `Person` must be implemented as well as **repositories** and **services**. 
`PersonDAO` and `BookDAO` must not be used, all interaction with DataBase must be done through **services**.

Approximate time to complete the task: 3-6 hours.

DataBase must contain 2 tables (entities) - `Person` and `Book`.
For both tables `id` should be autogenerated.

## Required functionality

### Old functionality from [**TechnicalTask1_LibraryApp**](https://github.com/IliaMalafeev/Spring_TechnicalTask1_LibraryApp)

* Page for adding, updating and deleting a `Person`
* Page for adding, updating and deleting a `Book`
* Page with the list of all people
    * Each `Person` is clickable - leads to person specific page.
* Page with the list of all books
    * Each `Book` is clickable - leads to book specific page.
* `Person` page - shows person entity info and a list of books
  currently bound to this person. If this person did not take any books yet,
  message "This person did not take any books yet." should be displayed
  instead of the books list
* `Book` page - shows book entity info and a name of the person, who currently
  has this book. If this book is currently free, message "This book is currently free."
  should be displayed instead
    * On this page, if the book is bound to someone, near to the persons name
      there must be a button "Unbind Book". This button is clicked by librarian when
      person returns the book to the library. When clicked, the book becomes
      free to take again and disappears from persons books list.
    * On this page, if the book is free, there must be a dropdown list (`<select>`)
      with all persons and a button "Assign Book". This button is clicked by librarian
      when someone wants to take the book home. When clicked, book must become assigned
      to that person and appear in persons books list.
* All fields must be validated with `@Valid` and `Spring Validator` if required.

### New functionality - brief (full description below)

* Implement **pagination** for books. There can be a lot of books, so controller method 
should be able to split the response into pages.
* Implement **sorting by year** for books.Controller method should be able to return books in sorted order.
* Implement **search books** page. User types initial letters of the book title into the field, searches 
and gets full book title and author. Also, if the book is currently taken by someone, 
user gets tha name of that person.
* Implement automatic check if the person has overdue books.

#### Pagination

Method `showAllBooks` inside `BooksController` should be able to take two parameters in the URL:
* `page` - tells the number of the page that should be returned;
* `books_per_page` - tells the amount of books on each page.

If request does not contain those keys, all books should be returned as usual.

For example request `http://localhost:8080/books?page=1&books_per_page=3` 
should return first page with first 3 books on it and so on.

#### Sorting

Method `showAllBooks` inside `BooksController` should be able to take following parameter in the URL:
* `sort_by_year`

If its value is `true` then response returned should be sorted based on the year of publication. 
If this parameter is not passed, then books are to be returned as usual.

For example request `http://localhost:8080/books?sort_by_year=true`
should return list of books sorted by year of publication.

**Pagination and sorting can be applied together, if all three parameters are passed in the URL.**

#### Search Page

* Search result must provide the book that is found and its current holder. If no books were found
message "No books were found" should be displayed.
* Search must be done based on first letters of books title (using JPA Repository).

#### Overdue check

If a person has taken a book more than 10 days ago and still did not return it - that book must be
highlighted red in this persons list of books.


## Implementation

Source code in this repository contains all required configurations and functionality,
as well as entities, controllers, validators, etc. It is a fully complete functional project.

If you want to run and test it - you have to create a test database on your local machine
and fill out missing fields in database.properties file
(do not forget to remove ".origin" from file name). I have also added sql script file
"postgres_table_creation_commands.sql" with commands to create the necessary tables.