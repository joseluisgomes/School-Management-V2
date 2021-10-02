package com.example.demo.school.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SECRETARY', 'ROLE_STUDENT')")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SECRETARY', 'ROLE_STUDENT')")
    public Student getStudent(@PathVariable(name = "studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SECRETARY')")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.registerNewStudent(student);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SECRETARY')")
    public void updateStudent(@PathVariable(name = "studentId") Long studentId,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, firstName, lastName, email);
    }
}
