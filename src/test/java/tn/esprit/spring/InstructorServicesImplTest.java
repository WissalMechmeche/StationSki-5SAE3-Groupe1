package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InstructorServicesImplTest {
    @Autowired
    private InstructorServicesImpl instructorService;

    @Autowired
    private IInstructorRepository instructorRepository;

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("yosr");
        instructor.setLastName("Chabeb");
        Instructor savedInstructor = instructorService.addInstructor(instructor);
        Assertions.assertNotNull(savedInstructor.getNumInstructor(), "Instructor ID should be generated");
        Assertions.assertEquals("yosr", savedInstructor.getFirstName());
    }

    @Test
    public void testRetrieveAllInstructors() {
        List<Instructor> instructors = instructorService.retrieveAllInstructors();
        Assertions.assertFalse(instructors.isEmpty(), "There should be instructors in the database");
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("eya");
        instructor.setLastName("Magdouli");
        Instructor savedInstructor = instructorService.addInstructor(instructor);
        savedInstructor.setFirstName("eya");
        Instructor updatedInstructor = instructorService.updateInstructor(savedInstructor);
        Assertions.assertEquals("eya", updatedInstructor.getFirstName(), "Instructor's name should be updated");
    }
}
