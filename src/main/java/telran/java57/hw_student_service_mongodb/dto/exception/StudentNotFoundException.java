package telran.java57.hw_student_service_mongodb.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Student with id = 2000 not found ")
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String id) {
        super("Student with id " + id + " not found");

    }
}
