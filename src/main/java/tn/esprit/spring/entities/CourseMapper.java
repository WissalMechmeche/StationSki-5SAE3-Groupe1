package tn.esprit.spring.entities;

import org.springframework.stereotype.Component;
import tn.esprit.spring.dto.CourseDTO;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getNumCourse(),
                course.getLevel(),
                course.getTypeCourse().name(),
                course.getSupport().name(),
                course.getPrice(),
                course.getTimeSlot(),
                course.getDescription(),
                course.getLocation()
        );
    }

    public Course toEntity(CourseDTO courseDTO) {
        return Course.builder()
                .numCourse(courseDTO.getNumCourse())
                .level(courseDTO.getLevel())
                .typeCourse(TypeCourse.valueOf(courseDTO.getTypeCourse()))
                .support(Support.valueOf(courseDTO.getSupport()))
                .price(courseDTO.getPrice())
                .timeSlot(courseDTO.getTimeSlot())
                .description(courseDTO.getDescription())
                .location(courseDTO.getLocation())
                .build();
    }
}
