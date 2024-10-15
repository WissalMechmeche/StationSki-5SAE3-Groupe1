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
public class CourseServicesImplMockTest {
    @InjectMocks
    private CourseServicesImpl courseService;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    public void testApplyDiscount() {
        // Créer un cours avec un prix et des attributs définis
        Course course = Course.builder().numCourse(1L).level(1)
                .typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(200.0F)
                .timeSlot(3).build();
        // Simuler le comportement du repository lors de la recherche du cours
        Mockito.when(courseRepository.findById(course.getNumCourse()))
                .thenReturn(Optional.of(course));
        // Simuler le comportement du repository lors de la sauvegarde après le discount
        Mockito.when(courseRepository.save(Mockito.any(Course.class)))
                .thenReturn(course);

        // Appliquer le discount
        Course discountedCourse = courseService.applyDiscount(course.getNumCourse());
        // Définir le prix attendu après le discount
        float expectedPrice = 160.0F;

        // Vérifier que le prix a été ajusté correctement
        Assertions.assertEquals(expectedPrice, discountedCourse.getPrice(), "Le prix après le discount doit être 160.0F");

        // Vérifier que le type de cours reste inchangé
        Assertions.assertEquals(TypeCourse.COLLECTIVE_CHILDREN, discountedCourse.getTypeCourse(),
                "Le type doit être COLLECTIVE_CHILDREN");

        // Vérification des appels au repository
        verify(courseRepository).findById(course.getNumCourse());
        verify(courseRepository).save(Mockito.any(Course.class));
    }

    @Test
    public void testSearchCourses() {
        // Create two courses for testing purposes
        Course course1 = Course.builder()
                .level(1).typeCourse(TypeCourse.COLLECTIVE_CHILDREN).price(50.0F).timeSlot(1)
                .location("Paris").description("Cours collectif pour enfants").build();

        Course course2 = Course.builder()
                .level(2).typeCourse(TypeCourse.INDIVIDUAL).price(100.0F).timeSlot(2)
                .location("Lyon").description("Cours individuel pour adultes").build();

        // Mock the repository's search behavior (assuming a custom method exists)
        Mockito.when(courseRepository.findAllByCriteria(Mockito.anyInt(), Mockito.isNull(), Mockito.isNull(), Mockito.isNull(),
                        Mockito.eq("Paris")))
                .thenReturn(Collections.singletonList(course1));

        // Perform the search
        List<Course> results = courseService.searchCourses(1, null, null, null, "Paris");

        // Assertions to verify the search results
        Assertions.assertEquals(1, results.size(), "There should be 1 course matching the search criteria");
        Assertions.assertEquals("Cours collectif pour enfants", results.get(0).getDescription(),
                "The description should match the course");

        // Verify that the repository's search method was called with the correct parameters
        verify(courseRepository).findAllByCriteria(1, null, null, null, "Paris");
    }





}
