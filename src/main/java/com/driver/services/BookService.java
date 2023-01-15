package com.driver.services;

import com.driver.BookRequestDto;
import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.models.Genre;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;
    @Autowired
    private AuthorRepository authorRepository2;

    public void createBook(Book book){
             
        if(book.getAuthor()!=null) {
            Author author = book.getAuthor();
            author = authorRepository2.findById(author.getId()).get();
            List<Book> bookList = author.getBooksWritten();
            if(bookList==null) bookList=new ArrayList<>();
            bookList.add(book);
            author.setBooksWritten(bookList);

            authorRepository2.save(author);
            return;
        }
        bookRepository2.save(book);
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = new ArrayList<>(); //find the elements of the list by yourself
        //System.out.println(genre+""+available+""+author);

        try{

            if(author.length()>0){
                books = bookRepository2.findBooksByAuthor(author,available);
            }
            else{
                books = bookRepository2.findByAvailability(available);
            }

            if(genre.length()>0){

                List<Book> ans = new ArrayList<>();

                for(Book b : books){

                    if(b.getGenre().toString().equals(genre)){
                        //System.out.println(b.getGenre().toString());
                        ans.add(b);
                    }
                }

                return ans;
            }

            return books;
        }
        catch (Exception e){
            //System.out.println(e.getMessage());
            return null;
        }

    }
}
