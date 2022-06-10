package securityproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import securityproject.models.Student;
import securityproject.services.StudentService;

@Controller
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // find all
    @GetMapping
    public String showAllStudents(Model model) {

        model.addAttribute("allStudents", studentService.findAll());

        return "students";
    }

    // save
    @GetMapping("/save/page")
    public String showSavePage(Model model) {

        model.addAttribute("newStudent", new Student());

        return "savepage";
    }

    @PostMapping("/save")
    public String saveStudent(Student student) {

        studentService.save(student);

        return "redirect:/api/students";
    }

    // delete
    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {

        studentService.deleteById(studentId);

        return "redirect:/api/students";
    }

}
