package com.driver.services;

import com.driver.models.Student;
import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.repositories.CardRepository;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {


    @Autowired
    CardRepository cardRepository3;

     public Card createAndReturn(Student student){
        
        try{
            Card newCard = new Card();

            student.setCard(newCard);
            newCard.setStudent(student);

            cardRepository3.save(newCard);
            return newCard;
        }
        catch(Exception e){
            return null;
        }

    }

    public void deactivateCard(int student_id){
        
        try{
            cardRepository3.deactivateCard(student_id, CardStatus.DEACTIVATED.toString());
            System.out.println("delete");
        }
        catch(Exception e){
            
        }
        
    }
}
