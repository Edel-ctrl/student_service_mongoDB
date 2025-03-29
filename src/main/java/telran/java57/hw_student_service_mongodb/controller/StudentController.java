package telran.java57.hw_student_service_mongodb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java57.hw_student_service_mongodb.dto.NewStudentDto;
import telran.java57.hw_student_service_mongodb.dto.ScoreDto;
import telran.java57.hw_student_service_mongodb.dto.StudentDto;
import telran.java57.hw_student_service_mongodb.dto.StudentUpdateDto;
import telran.java57.hw_student_service_mongodb.service.StudentService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class StudentController {

    final StudentService studentService;

    @PostMapping("/student")
    public boolean addStudent(@RequestBody NewStudentDto newStudentDto) {
        return studentService.addStudent(newStudentDto);
    }

    @GetMapping("/student/{id}")
    public StudentDto findStudent(@PathVariable int id) {
        return studentService.findStudent(id);
    }

    @DeleteMapping("/student/{id}")
    public StudentDto removeStudent(@PathVariable int id) {
        return studentService.removeStudent(id);
    }

    @PutMapping("/student/{id}")
    public NewStudentDto updateStudent(@PathVariable int id, @RequestBody StudentUpdateDto studentUpdateDto) {
        return studentService.updateStudent(id, studentUpdateDto);
    }

    @PutMapping("/score/student/{id}")
    public boolean addScore(@PathVariable int id, @RequestBody ScoreDto scoreDto) {
        return studentService.addScore(id, scoreDto);
    }

    @GetMapping("/students/name/{name}")
    public List<StudentDto> findStudentsByName(@PathVariable String name) {
        return studentService.findStudentsByName(name);
    }

    @PostMapping("quantity/students")
    public Integer getStudentNamesQuantity(@RequestBody Set<String> names) {
        return studentService.getStudentNamesQuantity(names);
    }

    @GetMapping("/students/exam/{exam}/minscore/{minScore}")
    public List<StudentDto> getStudentByExamMinScore(@PathVariable String exam, @PathVariable Integer minScore) {
        return studentService.getStudentByExamMinScore(exam, minScore);
    }
}
