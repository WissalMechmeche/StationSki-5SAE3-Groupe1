package tn.esprit.spring;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CourseServicesImplMockTest {
    @InjectMocks
    private CourseServicesImpl courseService;

    @Mock
    private ICourseRepository courseRepository;
    @Test
    void retrieveAllCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1,course2));

        List<Course> courses = courseService.retrieveAllCourses();

        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void addCourseTest() {

        Course course = Course.builder()
                .description("test1")
                .typeCourse(TypeCourse.INDIVIDUAL)
                .support(Support.SKI)
                .price(10.0f)
                .timeSlot(1)
                .level(1).build();

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseService.addCourse(course);

        assertNotNull(savedCourse);
        assertEquals("test1", savedCourse.getDescription());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);
        Course updatedCourse = courseService.updateCourse(course);
        assertNotNull(updatedCourse);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void retrieveCourse() {
        Long courseId = 1L;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Course retrievedCourse = courseService.retrieveCourse(courseId);
        assertNotNull(retrievedCourse);
        verify(courseRepository, times(1)).findById(courseId);
    }
    @Test
    void deleteCourseTest() {

        Long courseId = 1L;
        doNothing().when(courseRepository).deleteById(courseId);

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    void testApplyDiscount() {

        Course course = Course.builder().numCourse(1L).level(1)
                .typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(200.0F)
                .timeSlot(3).build();

        when(courseRepository.findById(course.getNumCourse()))
                .thenReturn(Optional.of(course));


        when(courseRepository.save(Mockito.any(Course.class)))
                .thenReturn(course);


        Course discountedCourse = courseService.applyDiscount(course.getNumCourse());

        float expectedPrice = 160.0F;


        assertEquals(expectedPrice, discountedCourse.getPrice(), "Le prix après le discount doit être 160.0F");


        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, discountedCourse.getTypeCourse(),
                "Le type doit être COLLECTIVE_CHILDREN");


        verify(courseRepository).findById(course.getNumCourse());
        verify(courseRepository).save(Mockito.any(Course.class));
    }

    @Test
    void testSearchCourses() {

        Course course1 = Course.builder()
                .level(1).typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(50.0F).timeSlot(1)
                .location("Paris").description("Cours collectif pour enfants").build();




        when(courseRepository.findAllByCriteria(Mockito.anyInt(), Mockito.isNull(), Mockito.isNull(), Mockito.isNull(),
                        Mockito.eq("Paris")))
                .thenReturn(Collections.singletonList(course1));


        List<Course> results = courseService.searchCourses(1, null, null, null, "Paris");


        assertEquals(1, results.size(), "There should be 1 course matching the search criteria");
        assertEquals("Cours collectif pour enfants", results.get(0).getDescription(),
                "The description should match the course");


        verify(courseRepository).findAllByCriteria(1, null, null, null, "Paris");
    }





}
