package com.example.demo.school.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    public final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Student with id %s does not exist", studentId)
                ));
    }

    public void registerNewStudent(Student student) {
        boolean present = studentRepository.findStudentByEmail(student.getEmail())
                .isPresent();
        if (present)
            studentRepository.save(student);
        else
            throw new IllegalStateException(
                    String.format("Email %s already taken", student.getEmail())
            );
    }

    public void updateStudent(Long studentId,
                              String firstName,
                              String lastName,
                              String email) {
        Student student = getStudent(studentId);

        if (firstName.length() > 0 && !(firstName.equals(student.getFirstName())))
            student.setFirstName(Objects.requireNonNull(firstName));
        if (lastName.length() > 0 && !(lastName.equals(student.getLastName())))
            student.setLastName(Objects.requireNonNull(lastName));

        if (email.length() > 0 && !(email.equals(student.getEmail()))) {
            Optional<Student> optionalStudent = studentRepository.findStudentByEmail(
                    Objects.requireNonNull(email)
            );

            if (optionalStudent.isPresent())
                throw new IllegalStateException(
                        String.format("Email %s already taken", email)
                );
            else
                student.setEmail(email);
        }
    }
}
