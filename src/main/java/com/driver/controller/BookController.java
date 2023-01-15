package com.driver.controller;

import com.driver.BookRequestDto;
import com.driver.models.Book;
import com.driver.models.Genre;
import com.driver.models.Student;
import com.driver.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("book")
public class BookController {

  @Autowired
    BookService bookService;

    @PostMapping("/")
    public ResponseEntity<String> createBook(@RequestBody()Book book){
        bookService.createBook(book);
        return new ResponseEntity("Book has been created!",HttpStatus.CREATED);
    }


    //    Get Books: GET /book/ Pass nullable parameters genre, availability, and author to filter out books For example: i) If genre=”X”, availability = true, and author=null; we require the list of all books which are available and have genre “X”. Note that these books can be written by any author. ii) If genre=”Y”, availability = false, and author=”A”; we require the list of all books which are written by author “A”, have genre “Y”, and are currently unavailable. Return success message wrapped in a ResponseEntity object Controller Name - getBooks
//
    @GetMapping("/")
    public ResponseEntity getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){

        List<Book> bookList = bookService.getBooks(genre,available,author); //find the elements of the list by yourself

        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
