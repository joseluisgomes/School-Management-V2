package com.example.demo.school.enrolment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnrolmentId implements Serializable {

    @Column(
            name = "student_id",
            nullable = false
    )
    private Long studentId;

    @Column(
            name = "course_id",
            nullable = false
    )
    private Long courseId;

    public EnrolmentId(Long studentId,
                       Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public EnrolmentId() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EnrolmentId that))
            return false;
        return studentId.equals(that.studentId) && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return 31 * courseId.hashCode() + studentId.hashCode();
    }
}
