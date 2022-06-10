package securityproject.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import securityproject.models.Student;
import securityproject.repositories.StudentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepo studentRepo,
                          PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public void save(Student student) {

        String email = student.getEmail();

        Optional<Student> studentOptional = studentRepo.findByEmail(email);

        if (studentOptional.isPresent()) {
            throw new IllegalStateException(
                    "email = " + email + " already in use!"
            );
        }

        // password encode
        String password = student.getPassword();

        String encodedPassword = passwordEncoder.encode(password);

        student.setPassword(encodedPassword);

        studentRepo.save(student);
    }

    public void deleteById(Long studentId) {
        studentRepo.deleteById(studentId);
    }
}
