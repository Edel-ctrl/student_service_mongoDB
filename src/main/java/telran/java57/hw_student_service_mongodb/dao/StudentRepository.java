package telran.java57.hw_student_service_mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.hw_student_service_mongodb.model.Student;

import java.util.List;
import java.util.Set;


public interface StudentRepository extends MongoRepository<Student, Integer> {

    List<Student> findByNameIgnoreCase(String name);

    long countByNameIn(Set<String> names);

}
