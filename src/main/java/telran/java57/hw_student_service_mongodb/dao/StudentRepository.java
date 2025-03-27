package telran.java57.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.model.Student;

import java.util.List;


public interface StudentRepository extends MongoRepository<Student, Integer> {
List <Student> findByName(String name);

}
