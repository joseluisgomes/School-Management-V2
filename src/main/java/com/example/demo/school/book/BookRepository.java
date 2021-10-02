package com.example.demo.school.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @Query("SELECT s FROM Book s WHERE s.bookName = ?1")
    Optional<Book> findBookByBookName(String bookName);
}
