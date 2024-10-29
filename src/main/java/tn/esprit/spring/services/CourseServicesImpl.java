package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class CourseServicesImpl implements  ICourseServices{

    private ICourseRepository courseRepository;

    @Override
    public List<Course> retrieveAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course retrieveCourse(Long numCourse) {
        return courseRepository.findById(numCourse).orElse(null);
    }

    @Override
    public void deleteCourse(Long numCourse) {

        courseRepository.deleteById(numCourse);
    }
    @Override
    public Course applyDiscount(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));


        float discountPercentage;
        switch (course.getTypeCourse()) {
            case COLLECTIVE_CHILDREN:
                discountPercentage = 20.0F;
                break;
            case COLLECTIVE_ADULT:
                discountPercentage = 15.0F;
                break;
            case INDIVIDUAL:
                discountPercentage = 10.0F;
                break;
            default:
                throw new IllegalArgumentException("Unknown course type");
        }


        float discountAmount = course.getPrice() * (discountPercentage / 100);
        float newPrice = course.getPrice() - discountAmount;


        course.setPrice(newPrice);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> searchCourses(Integer level, TypeCourse typeCourse, Float minPrice, Float maxPrice, String location) {
        return courseRepository.findAllByCriteria(level, typeCourse, minPrice, maxPrice, location);
    }










}
