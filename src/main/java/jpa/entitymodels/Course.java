package jpa.entitymodels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer cId;

    @Column(name = "name")
    private String cName;

    @Column(name = "instructor")
    private String cInstructorName;

    //add constructors

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return cId.equals(course.cId) && cName.equals(course.cName) && cInstructorName.equals(course.cInstructorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cInstructorName);
    }
}
