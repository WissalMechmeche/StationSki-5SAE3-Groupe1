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
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CourseServicesImplMockTest {
    @InjectMocks
    private CourseServicesImpl courseService;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    void testApplyDiscount() {

        Course course = Course.builder().numCourse(1L).level(1)
                .typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(200.0F)
                .timeSlot(3).build();

        Mockito.when(courseRepository.findById(course.getNumCourse()))
                .thenReturn(Optional.of(course));


        Mockito.when(courseRepository.save(Mockito.any(Course.class)))
                .thenReturn(course);


        Course discountedCourse = courseService.applyDiscount(course.getNumCourse());

        float expectedPrice = 160.0F;


        Assertions.assertEquals(expectedPrice, discountedCourse.getPrice(), "Le prix après le discount doit être 160.0F");


        Assertions.assertEquals(TypeCourse.COLLECTIVE_CHILDREN, discountedCourse.getTypeCourse(),
                "Le type doit être COLLECTIVE_CHILDREN");

        // Vérification des appels au repository
        verify(courseRepository).findById(course.getNumCourse());
        verify(courseRepository).save(Mockito.any(Course.class));
    }

    @Test
    void testSearchCourses() {

        Course course1 = Course.builder()
                .level(1).typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(50.0F).timeSlot(1)
                .location("Paris").description("Cours collectif pour enfants").build();




        Mockito.when(courseRepository.findAllByCriteria(Mockito.anyInt(), Mockito.isNull(), Mockito.isNull(), Mockito.isNull(),
                        Mockito.eq("Paris")))
                .thenReturn(Collections.singletonList(course1));


        List<Course> results = courseService.searchCourses(1, null, null, null, "Paris");


        Assertions.assertEquals(1, results.size(), "There should be 1 course matching the search criteria");
        Assertions.assertEquals("Cours collectif pour enfants", results.get(0).getDescription(),
                "The description should match the course");


        verify(courseRepository).findAllByCriteria(1, null, null, null, "Paris");
    }





}
