package com.example.demo.school.student.card;

import com.example.demo.school.student.Student;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "StudentIdCard")
@Table(name = "student_id_card")
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @OneToOne(cascade = {PERSIST, REMOVE})
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_card_fk"
            )
    )
    private Student student;

    @Column(
            name = "card_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String cardNumber;

    public StudentIdCard(Student student,
                         String cardNumber) {
        this.student = Objects.requireNonNull(student);
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard() {
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = Objects.requireNonNull(student);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StudentIdCard that))
            return false;
        return id.equals(that.id) && student.equals(that.student);
    }

    @Override
    public int hashCode() {
        /*
            A nice property of 31 is that the multiplication
            can be replaced by a shift and a subtraction for better performance
            on some architectures : 31 * i == (i << 5) - i
         */
        return 31 * id.hashCode() + student.hashCode();
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
