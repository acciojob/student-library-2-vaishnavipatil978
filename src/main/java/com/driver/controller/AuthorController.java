package com.driver.controller;

import com.driver.models.Author;
import com.driver.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/")
    public ResponseEntity createAuthor(@RequestBody Author author){
        authorService.create(author);
        return new ResponseEntity<>("Author added successfully!", HttpStatus.CREATED);
    }

}
