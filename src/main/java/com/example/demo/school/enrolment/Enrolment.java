package com.example.demo.school.enrolment;

import com.example.demo.school.Course;
import com.example.demo.school.student.Student;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(
            name = "student_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "enrolment_student_id_fk"
            )

    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    private Course course;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDate createdAt;

    public Enrolment(EnrolmentId id,
                     Student student,
                     Course course,
                     LocalDate createdAt) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
    }

    public Enrolment() {
    }

    public EnrolmentId getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = Objects.requireNonNull(course);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Enrolment enrolment))
            return false;
        return id.equals(enrolment.id) && student.equals(enrolment.student);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + student.hashCode();
    }
}
