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
        
        try{
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        /*if(bookRepository5.existsById(bookId)==false || bookRepository5.findById(bookId).get().isAvailable()==false){
            throw new Exception("Book is either unavailable or not present");
        }
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        if(cardRepository5.existsById(cardId)==false || cardRepository5.findById(cardId).get().getCardStatus().equals(CardStatus.DEACTIVATED)){
            throw new Exception("Card is invalid");
        }
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        if(cardRepository5.findById(cardId).get().getBooks().size()>=max_allowed_books){
            throw new Exception("Book limit has reached for this card");
        }*/
        // If it fails: throw new Exception("Book limit has reached for this card");
        Transaction t1 = new Transaction();

        Card card = cardRepository5.findById(cardId).get();
        Book book = bookRepository5.findById(bookId).get();
        t1.setBook(book);
        t1.setCard(card);
            
         if(book==null || book.isAvailable()==false){
            t1.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(t1);
            throw new Exception("Book is either unavailable or not present");
        }
        if(card==null || card.getCardStatus().equals(CardStatus.DEACTIVATED)){
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
        t1.setIssueOperation(true);

        List<Transaction> CardtransactionList = card.getTransactions();
        CardtransactionList.add(t1);
        card.setTransactions(CardtransactionList);

        List<Book> BookList = card.getBooks();
        BookList.add(book);
        card.setBooks(BookList);
        book.setAvailable(false);
        System.out.println(1234);
        book.setCard(card);
        System.out.println(1234);

        List<Transaction> BooktransactionList = book.getTransactions();
        BooktransactionList.add(t1);
        book.setTransactions(BooktransactionList);

        cardRepository5.save(card);

        card = cardRepository5.findById(cardId).get();
        CardtransactionList = card.getTransactions();

        String transactionId = null;


        for(Transaction t : CardtransactionList){
            if(t.getCard().getId()==cardId) transactionId=t.getTransactionId();
        }

        return transactionId;
        //If the transaction is successful, save the transaction to the list of transactions and return the id
        //Note that the error message should match exactly in all cases

        //return transactionId instead
            
        }
        catch(Exception e){
            throw e;
        }
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{
        
        try{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        LocalDateTime ltd = LocalDateTime.now();
        Date date = Timestamp.valueOf(ltd);
        long difference_In_Time
                = transaction.getTransactionDate().getTime() - date.getTime();
        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;
        int  extraDays = (int)difference_In_Days-getMax_allowed_days;

        if(extraDays>0) transaction.setFineAmount(fine_per_day * extraDays);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = new Transaction();

        if(extraDays>0)returnBookTransaction.setFineAmount(fine_per_day*extraDays);
        else returnBookTransaction.setFineAmount(0);

        Card card = cardRepository5.findById(cardId).get();
        Book book = bookRepository5.findById(bookId).get();

        returnBookTransaction.setIssueOperation(false);
        returnBookTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        returnBookTransaction.setCard(card);
        returnBookTransaction.setBook(book);

        book.setAvailable(true);
        book.setCard(null);
        List<Book> bookList = card.getBooks();
        bookList.remove(book);
        card.setBooks(bookList);

        List<Transaction> CardtransactionList = card.getTransactions();
        CardtransactionList.add(returnBookTransaction);
        card.setTransactions(CardtransactionList);

        List<Transaction> BooktransactionList = book.getTransactions();
        BooktransactionList.add(returnBookTransaction);
        book.setTransactions(BooktransactionList);

        transactionRepository5.save(returnBookTransaction);
        cardRepository5.save(card);

        card = cardRepository5.findById(cardId).get();
        CardtransactionList = card.getTransactions();

        int size = CardtransactionList.size();

        return CardtransactionList.get(size-1); //return the transaction after updating all details
            
        }
        catch(Exception e){
            throw e;
        }
    }
}
