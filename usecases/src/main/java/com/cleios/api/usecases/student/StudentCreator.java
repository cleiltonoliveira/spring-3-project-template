package com.cleios.api.usecases.student;

import com.cleios.api.usecases.student.adapter.StudentAdapter;
import com.cleios.api.domain.model.Student;
import com.cleios.api.usecases.exception.ResourceConflictException;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
public class StudentCreator {

    private final StudentAdapter studentAdapter;

    @Inject
    public StudentCreator(StudentAdapter studentAdapter) {
        this.studentAdapter = studentAdapter;
    }

    public Student create(Student student){
        verifyIfEmailExists(student.getEmail());
        return  studentAdapter.save(student);
    }

    private void verifyIfEmailExists(String email) {
        if (studentAdapter.existsByEmail(email)) {
            throw new ResourceConflictException("An account already exists with this email");
        }
    }
}
