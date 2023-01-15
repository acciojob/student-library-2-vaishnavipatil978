package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.time.*;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {


        Transaction t1 = new Transaction();

        Card card = cardRepository5.findById(cardId).get();
        Book book = bookRepository5.findById(bookId).get();

        t1.setBook(book);
        t1.setCard(card);
        t1.setIssueOperation(true);
            
         if(!bookRepository5.findById(bookId).isPresent() || !bookRepository5.findById(bookId).get().isAvailable()){
            t1.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(t1);
            throw new Exception("Book is either unavailable or not present");
        }
        if(!cardRepository5.findById(cardId).isPresent() || cardRepository5.findById(cardId).get().getCardStatus().equals(CardStatus.DEACTIVATED)){
            t1.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(t1);
            throw new Exception("Card is invalid");
        }

        if(card.getBooks().size()>=max_allowed_books){
            t1.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(t1);
            throw new Exception("Book limit has reached for this card");
        }
        
            
        t1.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        List<Book> BookList = card.getBooks();
        BookList.add(book);
        card.setBooks(BookList);
        book.setAvailable(false);
        book.setCard(card);

        List<Transaction> BooktransactionList = book.getTransactions();
        BooktransactionList.add(t1);
        book.setTransactions(BooktransactionList);

        cardRepository5.save(card);
        transactionRepository5.save(t1);

        return t1.getTransactionId();
        //If the transaction is successful, save the transaction to the list of transactions and return the id
        //Note that the error message should match exactly in all cases

        //return transactionId instead

    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        Date issueDate = transaction.getTransactionDate();
        long time = Math.abs(System.currentTimeMillis()-issueDate.getTime());
        long extraDays = TimeUnit.DAYS.convert(time,TimeUnit.MILLISECONDS);

        int fine = 0;
        if(extraDays>getMax_allowed_days){
            fine=(int)(extraDays-getMax_allowed_days)*fine_per_day;
        }

        Transaction returnBookTransaction  = new Transaction();

        Card card = cardRepository5.findById(cardId).get();
        Book book = bookRepository5.findById(bookId).get();

        returnBookTransaction.setIssueOperation(false);
        returnBookTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        returnBookTransaction.setCard(card);
        returnBookTransaction.setBook(book);
        returnBookTransaction.setFineAmount(fine);

        book.setAvailable(true);
        book.setCard(null);
        List<Book> bookList = card.getBooks();
        bookList.remove(book);
        card.setBooks(bookList);

        List<Transaction> BooktransactionList = book.getTransactions();
        BooktransactionList.add(returnBookTransaction);
        book.setTransactions(BooktransactionList);

        cardRepository5.save(card);

        return returnBookTransaction; //return the transaction after updating all details

    }
}
