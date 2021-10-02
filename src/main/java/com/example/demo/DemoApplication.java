package com.example.demo;

import com.example.demo.school.Course;
import com.example.demo.school.book.Book;
import com.example.demo.school.enrolment.Enrolment;
import com.example.demo.school.enrolment.EnrolmentId;
import com.example.demo.school.student.Student;
import com.example.demo.school.student.StudentRepository;
import com.example.demo.school.student.card.StudentIdCard;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Faker faker = Faker.instance();
			Name name = faker.name();
			int size = 20; // Number of elements present on the database

			List<Student> students = generateRandomStudents(faker, name, size);
			List<Course> courses = generateRandomCourses(faker, size);
			AtomicInteger iterator = new AtomicInteger(0);

			students.forEach(student -> {
				student.setStudentIdCard(
						new StudentIdCard(
								student,
								String.valueOf(faker.number().numberBetween(1, 1250))
						)
				);
				student.registerNewBook(
						new Book(
								faker.book().title(),
								LocalDate.now()
						)
				);
				student.registerNewEnrolment(
						new Enrolment(
								new EnrolmentId(
										student.getId(),
										courses.get(iterator.get()).getId()
								),
								student,
								courses.get(iterator.get()),
								LocalDate.now()
						)
				);
				iterator.incrementAndGet();
			});

   			studentRepository.saveAll(students);
		};
	}

	private List<Student> generateRandomStudents(Faker faker,
												 Name name,
												 int size) {
		List<Student> students = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			students.add(
					new Student(
						name.firstName(),
						name.lastName(),
						String.format("%s@gmail.com", name.username()),
						faker.number().numberBetween(18, 34)
				)
			);
		}
		return students;
	}

	private List<Course> generateRandomCourses(Faker faker, int size) {
		List<Course> courses = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			courses.add(
					new Course(
							faker.job().field(),
							faker.university().name()
					)
			);
		}
		return courses;
	}
}
