package tn.esprit.spring.services;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;

import java.util.List;

public interface ICourseServices {

    List<Course> retrieveAllCourses();

    Course  addCourse(Course  course);

    Course updateCourse(Course course);

    Course retrieveCourse(Long numCourse);

    void deleteCourse(Long numCourse);

    public Course applyDiscount(Long courseId);

    public List<Course> searchCourses(Integer level, TypeCourse typeCourse, Float minPrice, Float maxPrice, String location);


}
