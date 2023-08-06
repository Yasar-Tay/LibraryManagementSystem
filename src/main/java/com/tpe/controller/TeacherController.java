package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers") //http;//localhost:8081/teachers
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Map<String, String>> saveTeacher(@Valid @RequestBody Teacher teacher){
        teacherService.saveTeacher(teacher);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("message", "Teacher is saved successfully");
        responseMap.put("status", "True");

        return new ResponseEntity<>(responseMap, HttpStatus.CREATED); //201
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> listAllTeachers(){
        List<Teacher> teacherList = teacherService.findAllTeachers();
        return new ResponseEntity<>(teacherList, HttpStatus.OK);
    }

}
