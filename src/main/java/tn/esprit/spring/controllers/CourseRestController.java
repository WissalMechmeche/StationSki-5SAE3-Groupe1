package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.dto.CourseDTO;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.CourseMapper;
import tn.esprit.spring.services.ICourseServices;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "\uD83D\uDCDA Course Management")
@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CourseRestController {

    private final ICourseServices courseServices;

    private final CourseMapper courseMapper;


    @Operation(description = "Add Course")
    @PostMapping("/add")
    public ResponseEntity<CourseDTO> addCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course savedCourse = courseServices.addCourse(courseMapper.toEntity(courseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toDTO(savedCourse));
    }


    @Operation(description = "Retrieve all Courses")
    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courseDTOS = courseServices.retrieveAllCourses().stream()
                .map(courseMapper::toDTO)
                .toList();  // Utilisation de Stream.toList() à la place de Collectors.toList()
        return ResponseEntity.ok(courseDTOS);
    }


    @Operation(description = "Update Course")
    @PutMapping("/update")
    public ResponseEntity<CourseDTO> updateCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO); // Use the mapper
        Course updatedCourse = courseServices.updateCourse(course);
        return ResponseEntity.ok(courseMapper.toDTO(updatedCourse)); // Use the mapper
    }

    @Operation(description = "Retrieve Course by Id")
    @GetMapping("/get/{id-course}")
    public ResponseEntity<CourseDTO> getById(@PathVariable("id-course") Long numCourse) {
        Course course = courseServices.retrieveCourse(numCourse);
        if (course == null) {
            throw new EntityNotFoundException("Course not found with ID " + numCourse);
        }
        return ResponseEntity.ok(courseMapper.toDTO(course)); // Use the mapper
    }

    @Operation(description = "Delete Course by Id")
    @DeleteMapping("/delete/{id-course}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id-course") Long numCourse) {
        courseServices.deleteCourse(numCourse);
        return ResponseEntity.noContent().build();
    }



}
