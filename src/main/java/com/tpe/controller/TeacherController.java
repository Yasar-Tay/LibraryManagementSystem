package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDTO;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers") //http://localhost:8081/teachers
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

    @GetMapping("/{id}") //http://localhost:8081/teachers/{id}
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long id){
        Teacher foundTeacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(foundTeacher);
    }

    @GetMapping("/query") //http://localhost:8081/teachers/query?id={id}
    public ResponseEntity<Teacher> getTeacherByIdWithRequest(@RequestParam Long id){
        Teacher foundTeacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(foundTeacher);
    }

    @DeleteMapping("/{id}") //http://localhost:8081/teachers/{id}
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id){
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(String.format("Teacher with id: %s is successfully deleted.", id), HttpStatus.OK);
    }

    //find Teacher by lastname
    @GetMapping("/byLastName") //http://localhost:8081/teachers/byLastName?lastName={lastName}
    public ResponseEntity<List<Teacher>> getTeacherByLastNamePath(@RequestParam String lastName){
        List<Teacher> foundTeachers = teacherService.findTeacherByLastName(lastName);
        return ResponseEntity.ok(foundTeachers);
    }

    //Update teacher by id
    /*@PutMapping("/{id}")
    public ResponseEntity<Map<String,Teacher>> updateTeacher(@Valid @PathVariable Long id, @RequestBody Teacher teacher){
        Teacher updatedTeacher = teacherService.updateTeacherById(id, teacher);
        Map<String,Teacher> response = new HashMap<>();
        response.put("Updated Teacher: ", updatedTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

    @GetMapping("/page") //http://localhost:8081/teachers/page?page=1&size=3&sort=name&direction=ASC/DESC
    public ResponseEntity<Page<Teacher>> getTeachersByPage(@RequestParam("page") int pageCount, //How Many Pages do we want
                                                           @RequestParam("size") int sizeOfOnePage, //How many objects will each page have
                                                           @RequestParam("sort") String sortProp, //Which property will be used for sorting
                                                           @RequestParam("direction")Sort.Direction direction) { //ASC or DESC

        Pageable pageable = PageRequest.of(pageCount, sizeOfOnePage, Sort.by(direction, sortProp));
        Page<Teacher> teacherPage = teacherService.getTeachersByPage(pageable);
        return ResponseEntity.ok(teacherPage);
    }

    //Update teacher by id - DTO
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Teacher>> updateTeacher(@PathVariable("id") Long teacherId,
                                                             @Valid @RequestBody TeacherDTO teacherDTO){
        Teacher updatedTeacher = teacherService.updateTeacherByDTO(teacherId, teacherDTO);
        Map<String,Teacher> response = new HashMap<>();
        response.put("Updated Teacher: ", updatedTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
