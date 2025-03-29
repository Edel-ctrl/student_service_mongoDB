package telran.java57.hw_student_service_mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import telran.java57.hw_student_service_mongodb.dao.StudentRepository;
import telran.java57.hw_student_service_mongodb.dto.*;
import telran.java57.hw_student_service_mongodb.dto.exception.StudentNotFoundException;
import telran.java57.hw_student_service_mongodb.model.Student;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public boolean addStudent(NewStudentDto newStudentDto) {
        if ( studentRepository.findById(newStudentDto.getId()).isPresent() ) {
            return false;
        }
        Student student = new Student(
                newStudentDto.getId(),
                newStudentDto.getName(),
                newStudentDto.getPassword()
        );
        studentRepository.save(student);
        return true;
    }

    @Override
    public StudentDto findStudent(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id + ""));
        return new StudentDto(student);
    }

    @Override
    public StudentDto removeStudent(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id + ""));
        studentRepository.deleteById(id);
        return new StudentDto(student);
    }

    @Override
    public NewStudentDto updateStudent(int id, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id + ""));

        if ( studentUpdateDto.getName() != null ) {
            student.setName(studentUpdateDto.getName());
        }
        if ( studentUpdateDto.getPassword() != null ) {
            student.setPassword(studentUpdateDto.getPassword());
        }

        studentRepository.save(student);

        return new NewStudentDto(student.getId(), student.getName(), student.getPassword());
    }

    @Override
    public boolean addScore(int id, ScoreDto scoreDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id + ""));
        boolean result = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
        studentRepository.save(student);
        return result;
    }

    @Override
    public List<StudentDto> findStudentsByName(String name) {
        return studentRepository
                .findByNameIgnoreCase(name)
                .stream()
                .map(StudentDto :: new)
                .toList();
    }


    @Override
    public Integer getStudentNamesQuantity(Set<String> names) {
        return (int) studentRepository.countByNameIn(names);

    }


    @Override
    public List<StudentDto> getStudentByExamMinScore(String exam, Integer minScore) {
        Query query = new Query(Criteria.where("scores." + exam).gte(minScore));
        return mongoTemplate.find(query, Student.class)
                .stream()
                .map(StudentDto :: new)
                .toList();
    }
}

