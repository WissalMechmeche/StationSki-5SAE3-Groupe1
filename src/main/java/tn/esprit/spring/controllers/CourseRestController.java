package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.dto.CourseDTO;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.services.ICourseServices;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "\uD83D\uDCDA Course Management")
@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CourseRestController {

    private final ICourseServices courseServices;

    @Operation(description = "Test webhooks")
    @GetMapping("/test")
    public String testWebhooks() {
        return "Test webhooks";
    }

    @Operation(description = "Add Course")
    @PostMapping("/add")
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        Course course = mapToEntity(courseDTO);
        Course savedCourse = courseServices.addCourse(course);
        return mapToDTO(savedCourse);
    }

    @Operation(description = "Retrieve all Courses")
    @GetMapping("/all")
    public List<CourseDTO> getAllCourses() {
        return courseServices.retrieveAllCourses()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    @Operation(description = "Update Course")
    @PutMapping("/update")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO) {
        Course course = mapToEntity(courseDTO);
        Course updatedCourse = courseServices.updateCourse(course);
        return mapToDTO(updatedCourse);
    }

    @Operation(description = "Retrieve Course by Id")
    @GetMapping("/get/{id-course}")
    public CourseDTO getById(@PathVariable("id-course") Long numCourse) {
        Course course = courseServices.retrieveCourse(numCourse);
        return mapToDTO(course);
    }


    private Course mapToEntity(CourseDTO courseDTO) {
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


    private CourseDTO mapToDTO(Course course) {
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
}
