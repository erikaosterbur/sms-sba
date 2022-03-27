package jpa.service;

import jpa.entitymodels.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {
    private static StudentService studentService;

    @BeforeAll
    public static void setUp(){
        studentService = new StudentService();
    }

    @Test
    public void getStudentByEmail(){
        //given
        Student expected = new Student();
        expected.setSName("Holmes Taffley");
        expected.setSEmail("htaffley6@columbia.edu");
        expected.setSPass("xowtOQ");

        //when
        Student actual = studentService.getStudentByEmail("htaffley6@columbia.edu");

        //then
        Assertions.assertEquals(expected, actual);
    }
}
