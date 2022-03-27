package jpa.entitymodels;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "student")

public class Student {

    @Id
    @Column(name = "email")
    private String sEmail;

    @Column(name = "name")
    private String sName;

    @Column(name = "password")
    private String sPass;

    //add constructors

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "student_email")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    private List<Course> sCourses = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return sEmail.equals(student.sEmail) && sName.equals(student.sName) && sPass.equals(student.sPass) && sCourses.equals(student.sCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sEmail, sName, sPass, sCourses);
    }
}
