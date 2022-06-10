package securityproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_gen"
    )
    @SequenceGenerator(
            name = "student_id_gen",
            sequenceName = "student_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;

    private String email;

    private String password;
}
