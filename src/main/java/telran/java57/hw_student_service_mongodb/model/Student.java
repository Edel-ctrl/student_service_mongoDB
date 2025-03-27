package telran.java57.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Document(collection = "students")
public class Student {
    int id;
    @Setter
    String name;
    @Setter
    String password;
    Map<String,Integer> scores = new HashMap<>();

    public Student(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public boolean addScore(String exam, int score){
        return scores.put(exam,score) == null;
    }
}
