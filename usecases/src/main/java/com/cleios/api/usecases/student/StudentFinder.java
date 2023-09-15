package com.cleios.api.usecases.student;

import com.cleios.api.usecases.student.adapter.StudentAdapter;
import com.cleios.api.domain.model.Student;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
public class StudentFinder {
    private final StudentAdapter studentAdapter;

    @Inject
    public StudentFinder(StudentAdapter studentAdapter) {
        this.studentAdapter = studentAdapter;
    }

    public List<Student> listAll(){
        return studentAdapter.listAll();
    }
}
