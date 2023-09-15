package com.cleios.api.web.controller.student;

import com.cleios.api.domain.model.Student;
import com.cleios.api.usecases.student.StudentCreator;
import com.cleios.api.usecases.student.StudentFinder;
import com.cleios.api.web.controller.student.dto.StudentCreationRequest;
import com.cleios.api.web.controller.student.dto.StudentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class StudentController {
    private StudentCreator studentCreator;
    private StudentFinder studentFinder;
    private ModelMapper modelMapper;

    @PostMapping("public/students")
    public ResponseEntity<?> createStudent(@RequestBody @Valid StudentCreationRequest studentCreationRequest) {
        var student = toStudent(studentCreationRequest);
        return new ResponseEntity<>(toStudentResponse(studentCreator.create(student)), HttpStatus.CREATED);
    }

    @GetMapping("public/students")
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(studentFinder.listAll().stream().map(this::toStudentResponse), HttpStatus.OK);
    }

    private StudentResponse toStudentResponse(Student student) {
        return modelMapper.map(student, StudentResponse.class);
    }

    private Student toStudent(StudentCreationRequest request) {
        return modelMapper.map(request, Student.class);
    }

}
