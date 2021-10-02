package com.example.demo.school.student.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {

    @Query("SELECT s FROM StudentIdCard s WHERE s.cardNumber = ?1")
    Optional<StudentIdCard> findStudentIdCardByCardNumber(String cardNumber);
}
