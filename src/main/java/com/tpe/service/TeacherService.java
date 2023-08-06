package com.tpe.service;

import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public void saveTeacher(Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());

        if (existingTeacher != null){
            throw new ConflictException(String.format("Teacher with email: %s already exists!", teacher.getEmail()));
        }

        teacherRepository.save(teacher);
    }

    public List<Teacher> findAllTeachers() {
        List<Teacher> teacherList = teacherRepository.findAll();

        if (teacherList.isEmpty())
            throw new ResourceNotFoundException("No teacher...");

        return teacherList;
    }
}
