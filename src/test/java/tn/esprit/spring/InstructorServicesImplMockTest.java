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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class InstructorServicesImplMockTest {
    @InjectMocks
    private InstructorServicesImpl instructorService;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Farah");
        instructor.setLastName("weslati");
        Mockito.when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor addedInstructor = instructorService.addInstructor(instructor);
        Assertions.assertNotNull(addedInstructor, "The added instructor should not be null");
        Assertions.assertEquals("Farah", addedInstructor.getFirstName());
        verify(instructorRepository).save(instructor);
    }

    @Test
    public void testRetrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        instructor1.setFirstName("wissal");
        Instructor instructor2 = new Instructor();
        instructor2.setFirstName("jesser");
        Mockito.when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));
        List<Instructor> instructors = instructorService.retrieveAllInstructors();
        Assertions.assertEquals(2, instructors.size(), "There should be 2 instructors retrieved");
        verify(instructorRepository).findAll();
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("Farah");
        Mockito.when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor updatedInstructor = instructorService.updateInstructor(instructor);
        Assertions.assertNotNull(updatedInstructor);
        Assertions.assertEquals("Farah", updatedInstructor.getFirstName());
        verify(instructorRepository).save(instructor);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        Instructor instructor = new Instructor();
        instructor.setFirstName("Farah");
        Course course = new Course();
        Mockito.when(courseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(course));
        Mockito.when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorService.addInstructorAndAssignToCourse(instructor, 1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(instructor, result);
        verify(courseRepository).findById(1L);
        verify(instructorRepository).save(instructor);
    }
    @Test
    public void testSearchInstructors() {
        //create instructors to test with
        Instructor instructor1 = new Instructor();
        instructor1.setFirstName("yosr");
        instructor1.setLastName("Chabeb");

        Instructor instructor2 = new Instructor();
        instructor2.setFirstName("eya");
        instructor2.setLastName("Magdouli");

        Mockito.when(instructorRepository.findByLastNameContaining("yosr")).thenReturn(Collections.singletonList(instructor1));
        List<Instructor> results = instructorService.searchInstructorsByLastName("yosr");
        Assertions.assertEquals(1, results.size(), "There should be 1 instructor matching the search criteria");
        Assertions.assertEquals("yosr", results.get(0).getFirstName(), "The first name should match the instructor");
        verify(instructorRepository).findByLastNameContaining("yosr");
    }

    @Test
    public void testRetrieveInstructorsWithPagination() {
        // Create a list of instructors for testing
        Instructor instructor1 = new Instructor();
        instructor1.setNumInstructor(1L);
        instructor1.setFirstName("yosr");
        instructor1.setLastName("Chabeb");

        Instructor instructor2 = new Instructor();
        instructor2.setNumInstructor(2L);
        instructor2.setFirstName("eya");
        instructor2.setLastName("Magdouli");
        List<Instructor> instructorList = Arrays.asList(instructor1, instructor2);
        Page<Instructor> pagedInstructors = new PageImpl<>(instructorList, PageRequest.of(0, 2), instructorList.size());
        Mockito.when(instructorRepository.findAll(PageRequest.of(0, 2))).thenReturn(pagedInstructors);
        List<Instructor> instructorsPage = instructorService.retrieveInstructorsWithPagination(0, 2);
        Assertions.assertEquals(2, instructorsPage.size(), "Should return 2 instructors for the first page");
        Assertions.assertEquals("yosr", instructorsPage.get(0).getFirstName(), "The first instructor should be yosr");
        Assertions.assertEquals("eya", instructorsPage.get(1).getFirstName(), "The second instructor should be eya");
        verify(instructorRepository).findAll(PageRequest.of(0, 2));
    }

}
