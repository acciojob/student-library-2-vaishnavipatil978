package com.driver.services;

import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.models.Student;
import com.driver.repositories.CardRepository;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;


    public Student getDetailsByEmail(String email){

        try{
            return studentRepository4.findByEmailId(email);
        }
        catch (Exception e){
            return null;
        }

    }

    public Student getDetailsById(int id){

        try{
            return studentRepository4.findById(id).get();
        }
        catch(Exception e){
            return null;
        }

    }

    public void createStudent(Student student){

        try{
            Card card = cardService4.createAndReturn(student);
//            card = studentRepository.findByEmailId(student.getEmailId()).getCard();
//            cardRepository.findById(card.getId()).get().setStudent(studentRepository.findByEmailId(student.getEmailId()));
        }
        catch (Exception e){

        }

    }

    public void updateStudent(Student student){
        try{
            studentRepository4.updateStudentDetails(student);
        }
        catch (Exception e){

        }
    }

    public void deleteStudent(int id){
        try{
            cardService4.deactivateCard(id);
            studentRepository4.deleteCustom(id);
        }
        catch (Exception e){}

        //Delete student and deactivate corresponding card
    }


}
