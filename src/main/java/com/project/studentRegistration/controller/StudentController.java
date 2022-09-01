package com.project.studentRegistration.controller;


import com.project.studentRegistration.model.Student;
import com.project.studentRegistration.repository.StudentRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.DrbgParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Student>> getAllStudents(){
        try {
            List<Student> studentList = new ArrayList<>();
            studentRepository.findAll().forEach(studentList::add);

            if (studentList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStudent/{studentNumber}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentNumber){
        Optional<Student> studentData = studentRepository.findById(studentNumber);

        if (studentData.isPresent()){
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student studentObject = studentRepository.save(student);

        return new ResponseEntity<>(studentObject, HttpStatus.OK);

    }

    @PostMapping("/updateStudent/{studentNumber}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long studentNumber, @RequestBody Student newStudentData){
        Optional<Student> oldStudentData = studentRepository.findById(studentNumber);

        if(oldStudentData.isPresent()){
            Student updatedStudentData = oldStudentData.get();
            updatedStudentData.setStudentName(newStudentData.getStudentName());

            Student studentObject = studentRepository.save(updatedStudentData);
            return new ResponseEntity<>(studentObject, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteStudent/{studentNumber}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable Long studentNumber){
        studentRepository.deleteById(studentNumber);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
