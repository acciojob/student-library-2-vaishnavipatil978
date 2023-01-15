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
        authorRepository1.save(author);
    }

    public List<Author> getByName(String name){
        List<Author> ans = new ArrayList<>();
        List<Author> allAuthors = authorRepository1.findAll();

        for(Author a : allAuthors){
            if(a.getName().equals(name)) ans.add(a);
        }

        return ans;
    }
}
