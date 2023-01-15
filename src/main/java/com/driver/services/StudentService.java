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
        return studentRepository4.findByEmailId(email);
    }

    public Student getDetailsById(int id){
       return studentRepository4.findById(id).get();
    }

    public void createStudent(Student student) throws Exception{

        try{
            Card card = cardService4.createAndReturn(student);
//            card = studentRepository.findByEmailId(student.getEmailId()).getCard();
//            cardRepository.findById(card.getId()).get().setStudent(studentRepository.findByEmailId(student.getEmailId()));
        }
        catch (Exception e){
            throw e;
        }

    }

    public void updateStudent(Student student){
        studentRepository4.updateStudentDetails(student);
    }

    public void deleteStudent(int id){
        cardService4.deactivateCard(id);
        studentRepository4.deleteCustom(id);
        //Delete student and deactivate corresponding card
    }

}
