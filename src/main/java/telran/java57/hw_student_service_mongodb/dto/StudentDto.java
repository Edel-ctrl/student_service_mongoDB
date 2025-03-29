package telran.java57.hw_student_service_mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.java57.hw_student_service_mongodb.model.Student;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    int id;
    String name;
    Map<String, Integer> scores;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.scores = student.getScores();
    }
}
