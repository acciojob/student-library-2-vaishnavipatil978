package com.driver.services;

import com.driver.models.Author;
import com.driver.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository1;

    public void create(Author author){
        try{
        authorRepository1.save(author);
        }
        catch(Exception e){}
    }

}
