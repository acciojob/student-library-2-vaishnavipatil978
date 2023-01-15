package com.driver.controller;

import com.driver.models.Card;
import com.driver.models.Student;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    //    Create a Student: POST /student/ Pass the Student object as request body Return success message wrapped in a ResponseEntity object Controller Name - createStudent
//
//    Update a Student: PUT /student/ Pass the Student object as request body Return success message wrapped in a ResponseEntity object Controller Name - updateStudent
//
//    Delete a Student: DELETE /student/ Pass the Student id as request parameter Return success message wrapped in a ResponseEntity object Controller Name - deleteStudent
//
//    Get Student using email: GET /student/studentByEmail/ Get Student using given email id Return success message wrapped in a ResponseEntity object Controller Name - getStudentByEmail
//
//    Get Student using Id: GET /student/studentById/ Get Student using given student id Return success message wrapped in a ResponseEntity object Controller Name - getStudentById
//

    @GetMapping("studentByEmail")
    public ResponseEntity getStudentByEmail(@RequestParam("email") String email){
        Student student = studentService.getDetailsByEmail(email);
        return new ResponseEntity<>(student+"Student details printed successfully ", HttpStatus.OK);
    }

    @GetMapping("studentById")
    public ResponseEntity getStudentById(@RequestParam("id") int id){
        Student student = studentService.getDetailsById(id);
        return new ResponseEntity<>(student+"Student details printed successfully ", HttpStatus.OK);
    }

    @PostMapping("createStudent")
    public ResponseEntity createStudent(@RequestBody() Student student){
        try{
            studentService.createStudent(student);
            return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("updateStudent")
    public ResponseEntity updateStudent(@RequestBody() Student student){
        studentService.updateStudent(student);
        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("deleteStudent")
    public ResponseEntity deleteStudent(@RequestParam("id") int id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }


}
