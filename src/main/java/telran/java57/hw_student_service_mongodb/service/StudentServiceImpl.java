package telran.java57.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import telran.java57.dao.StudentRepository;
import telran.java57.dto.NewStudentDto;
import telran.java57.dto.ScoreDto;
import telran.java57.dto.StudentDto;
import telran.java57.dto.StudentUpdateDto;
import telran.java57.dto.exception.StudentNotFoundException;
import telran.java57.model.Student;

import java.util.List;
import java.util.Set;
@RequiredArgsConstructor
//@Component
@Service
public class StudentServiceImpl implements StudentService{

   final StudentRepository studentRepository;

    @Override
    public boolean addStudent(NewStudentDto newStudentDto) {
        if (studentRepository
                .findById(newStudentDto.getId())
                .isPresent()){
            return false;
        }
        Student student = new Student(newStudentDto.getId(),
                newStudentDto.getName(),
                newStudentDto.getPassword());
        studentRepository.save(student);
        return true;
    }

    @Override
    public StudentDto findStudent(int id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id+""));
        return new StudentDto(id, student.getName(),student.getScores());
    }

    @Override
    public StudentDto removeStudent(int id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id+""));
        studentRepository
                .deleteById(id);
        return new StudentDto(id, student.getName(),student.getScores());
    }

    @Override
    public NewStudentDto updateStudent(int id, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id+""));
        if(studentUpdateDto.getName() !=null){
            student.setName(studentUpdateDto.getName());
        }
        if(studentUpdateDto.getPassword() !=null){
            student.setPassword(studentUpdateDto.getPassword());
        }
        studentRepository
                .save(student);
        return new NewStudentDto(student.getId(),
                student.getName(),
                student.getPassword());
    }

    @Override
    public boolean addScore(int id, ScoreDto scoreDto) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id+""));
        boolean res = student.addScore(scoreDto.getExamName(),scoreDto.getScore());
        studentRepository.save(student);
        return res;
    }

    @Override
    public List<StudentDto> findStudentsByName(String name) {
        return studentRepository
                .findAll()
                .stream()
                .filter(s -> name.equalsIgnoreCase(s.getName()))
                .map(s -> new StudentDto(s.getId(),s.getName(), s.getScores()))
                .toList();
    }

    @Override
    public Integer getStudentNamesQuantity(Set<String> names) {
        return (int) studentRepository.findAll()
                .stream()
                .filter(s -> names.contains(s.getName()))
                .count();
    }

    @Override
    public List<StudentDto> getStudentByExamMinScore(String exam, Integer minScore) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getScores().containsKey(exam) && s.getScores().get(exam) >= minScore)
                .map(s -> new StudentDto(s.getId(),s.getName(), s.getScores()))
                .toList();
    }
}
