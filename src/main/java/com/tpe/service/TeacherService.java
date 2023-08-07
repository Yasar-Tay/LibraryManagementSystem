package com.tpe.service;

import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public void saveTeacher(Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());

        if (existingTeacher != null) {
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

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Teacher with id: %s not found!", id)));
    }

    public void deleteTeacher(Long id) {
        Teacher foundTeacher = findTeacherById(id);
        teacherRepository.delete(foundTeacher);
    }

    public List<Teacher> findTeacherByLastName(String lastName) {
        List<Teacher> teacherList = teacherRepository.findByLastName(lastName);

        if (teacherList.isEmpty())
            throw new RuntimeException("No Teacher with last name : " + lastName);

        return teacherList;
    }

    public Teacher updateTeacherById(Long id, Teacher teacher) {
        //Found the teacher to be updated.
        Teacher teacherToBeUpdated = findTeacherById(id);

        //We need to check if updated email is used for another teacher before.
        if (!teacherToBeUpdated.getEmail().equals(teacher.getEmail())) {
            //If user entered a new email for the teacher, we need to check if the new email is already in use or not.
            Teacher foundTeacherByEmail = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacherByEmail != null) //if the search with the new email found an entity..
                throw new ConflictException("There is already a teacher with this email!");
        }

        teacherToBeUpdated.setName(teacher.getName());
        teacherToBeUpdated.setLastName(teacher.getLastName());
        teacherToBeUpdated.setEmail(teacher.getEmail());
        teacherToBeUpdated.setPhoneNumber(teacher.getPhoneNumber());
        teacherToBeUpdated.setBookList(teacher.getBookList());

        teacherRepository.save(teacherToBeUpdated);

        return teacherToBeUpdated;
    }
}
