package com.cleios.api.usecases.student.adapter;

import com.cleios.api.domain.model.Student;

import java.util.List;

public interface StudentAdapter {

    Student save(Student student);

    boolean existsByEmail(String email);

    List<Student> listAll();
}
