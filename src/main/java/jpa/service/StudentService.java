package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Student;
import jpa.entitymodels.Course;

import javax.persistence.*;
import java.util.*;

public class StudentService implements StudentDAO {
    private static final String PERSISTENCE_UNIT_NAME = "sms";

    private EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public List<Student> getAllStudents(){
        EntityManager em = emFactoryObj.createEntityManager();

        String sql = "SELECT s FROM Student s";
        TypedQuery<Student> query = em.createQuery(sql, Student.class);

        try{
            return query.getResultList();
        } catch (Exception e){
            return null;
        }
    }

    public Student getStudentByEmail(String sEmail){
        EntityManager em = emFactoryObj.createEntityManager();

        String sql = "SELECT s FROM Student s WHERE s.sEmail = :sEmail";
        TypedQuery<Student> query = em.createQuery(sql, Student.class);
        query.setParameter("sEmail", sEmail);

        try{
            return query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    public boolean validateStudent(String sEmail, String sPass){
        EntityManager em = emFactoryObj.createEntityManager();

        String sql = "SELECT s FROM Student s WHERE s.sEmail = :sEmail AND s.sPass = :sPass";
        TypedQuery<Student> query = em.createQuery(sql, Student.class);
        query.setParameter("sEmail", sEmail);
        query.setParameter("sPass", sPass);

        try{
            query.getSingleResult();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public void registerStudentToCourse(String sEmail, int cId){
        EntityManager em = emFactoryObj.createEntityManager();
        em.getTransaction().begin();

        //could have used getByEmail method
        String sqlStudent = "SELECT s FROM Student s WHERE s.sEmail = :sEmail";
        TypedQuery<Student> queryStudent = em.createQuery(sqlStudent, Student.class);
        queryStudent.setParameter("sEmail", sEmail);
        Student student = queryStudent.getSingleResult();

        String sqlCourse = "SELECT c FROM Course c WHERE c.cId = :cId";
        TypedQuery<Course> queryCourse = em.createQuery(sqlCourse, Course.class);
        queryCourse.setParameter("cId", cId);

        try{
            Course course = queryCourse.getSingleResult();
            if(student.getSCourses().contains(course)){
                System.out.println("You are already registered for this course");
            } else{
                student.getSCourses().add(course);

                em.merge(student);
                em.persist(student);

                em.getTransaction().commit();
                em.clear();
            }
        } catch (Exception e){
            System.out.println("Course not found " + e);
        }
    }

    public List<Course> getStudentCourses(String sEmail){
        EntityManager em = emFactoryObj.createEntityManager();

        String sql = "SELECT sc.sCourses FROM Student sc WHERE sc.sEmail = :sEmail";
        Query query = em.createQuery(sql);
        query.setParameter("sEmail", sEmail);

        try{
            return query.getResultList();
        } catch(Exception e){
            return null;
        }
    }
}

