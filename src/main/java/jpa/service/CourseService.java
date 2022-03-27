package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.*;

public class CourseService implements CourseDAO {

    private static final String PERSISTENCE_UNIT_NAME = "sms";

    private EntityManagerFactory emFactoryObj =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public List<Course> getAllCourses(){
        EntityManager em = emFactoryObj.createEntityManager();

        String sql = "SELECT c FROM Course c";
        TypedQuery<Course> query = em.createQuery(sql, Course.class);

        try{
            return query.getResultList();
        } catch (Exception e){
            return null;
        }
    }
}
