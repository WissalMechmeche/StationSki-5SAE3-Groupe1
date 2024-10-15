package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.List;



@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CourseServicesImplTest {

    @Autowired
    private CourseServicesImpl courseService ;


    @Test
    public void testApplyDiscount() {
        // Create a course with type COLLECTIVE_CHILDREN
        Course course = Course.builder()
                .level(1)
                .typeCourse(TypeCourse.COLLECTIVE_CHILDREN)
                .price(200.0F)
                .timeSlot(3)
                .build();

        // Add the course to the database
        Course savedCourse = courseService.addCourse(course);

        // Apply discount for COLLECTIVE_CHILDREN (20% discount in this example)
        Course discountedCourse = courseService.applyDiscount(savedCourse.getNumCourse());

        // Assertions to verify the discount was applied correctly
        Assertions.assertNotNull(discountedCourse.getNumCourse(), "The saved course should have a generated ID");
        Assertions.assertEquals(160.0F, discountedCourse.getPrice(), "Price after 20% discount should be 160.0F");
        Assertions.assertEquals(TypeCourse.COLLECTIVE_CHILDREN,
                discountedCourse.getTypeCourse(), "TypeCourse should still be COLLECTIVE_CHILDREN");

        // Clean up the test data by deleting the course
        courseService.deleteCourse(discountedCourse.getNumCourse());
    }

    @Test
    public void testSearchCourses() {
        // Créer et enregistrer quelques cours pour les tests
        Course course1 = Course.builder()
                .level(1)
                .typeCourse(TypeCourse.COLLECTIVE_CHILDREN)
                .price(50.0F)
                .timeSlot(1)
                .location("Paris")
                .description("Cours collectif pour enfants")
                .build();

        Course course2 = Course.builder()
                .level(2)
                .typeCourse(TypeCourse.INDIVIDUAL)
                .price(100.0F)
                .timeSlot(2)
                .location("Lyon")
                .description("Cours individuel pour adultes")
                .build();

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        // Rechercher des cours
        List<Course> results = courseService.searchCourses(1, null, null, null, "Paris");

        // Assertions pour vérifier les résultats
        Assertions.assertEquals(1, results.size(), "Il devrait y avoir 1 cours correspondant à la recherche");
        Assertions.assertEquals("Cours collectif pour enfants", results.get(0).getDescription(),
                "La description doit correspondre");

        // Nettoyage des données de test
        courseService.deleteCourse(course1.getNumCourse());
        courseService.deleteCourse(course2.getNumCourse());
    }




}
