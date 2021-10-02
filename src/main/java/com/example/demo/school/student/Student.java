package com.example.demo.school.student;

import com.example.demo.school.book.Book;
import com.example.demo.school.enrolment.Enrolment;
import com.example.demo.school.student.card.StudentIdCard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = @UniqueConstraint(
                name = "student_email_unique",
                columnNames = "email"
        )
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private int age;

    @JsonIgnore
    @OneToOne(
            cascade = {PERSIST, REMOVE},
            mappedBy = "student",
            orphanRemoval = true // Delete the student => Remove the student's card
    )
    private StudentIdCard studentIdCard; // Student's (school) card

    @JsonIgnore
    @OneToMany(
            cascade = {PERSIST, REMOVE},
            mappedBy = "student", // Delete the student => Remove all the student's books
            orphanRemoval = true
    )
    private final List<Book> books = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            cascade = {PERSIST, REMOVE},
            mappedBy = "student",
            orphanRemoval = true // Delete the student => Remove all the student's enrolments
    )
    private final List<Enrolment> enrolments = new ArrayList<>();

    public Student(String firstName,
                   String lastName,
                   String email,
                   int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public StudentIdCard getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = Objects.requireNonNull(studentIdCard);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void registerNewBook(Book book) {
        if (!(books.contains(book))) {
            books.add(Objects.requireNonNull(book));
            book.setStudent(this);
        } else
            throw new IllegalStateException(
                    "The student contains the given book"
            );
    }

    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            book.setStudent(null);
        } else
            throw new IllegalStateException(
                    "The student doesn't contain the given book"
            );
    }

    public void registerNewEnrolment(Enrolment enrolment) {
        if (!(enrolments.contains(enrolment))) {
            enrolments.add(Objects.requireNonNull(enrolment));
            enrolment.setStudent(this);
        } else
            throw new IllegalStateException(
                    "The student contains the given enrolment"
            );
    }

    public void removeEnrolment(Enrolment enrolment) {
        if (enrolments.contains(enrolment)) {
            enrolments.remove(enrolment);
            enrolment.setStudent(null);
        } else
            throw new IllegalStateException(
                    "The student doesn't contain the given enrolment"
            );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Student student))
            return false;
        return id.equals(student.id) && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        /*
            A nice property of 31 is that the multiplication
            can be replaced by a shift and a subtraction for better performance
            on some architectures : 31 * i == (i << 5) - i
         */
        return 31 * id.hashCode() + email.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
