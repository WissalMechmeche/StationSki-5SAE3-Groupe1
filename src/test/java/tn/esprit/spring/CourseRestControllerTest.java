package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.spring.controllers.CourseRestController;
import tn.esprit.spring.dto.CourseDTO;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.CourseMapper;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.services.ICourseServices;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CourseRestControllerTest {

    @Mock
    private ICourseServices courseServices;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseRestController courseRestController;

    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setNumCourse(1L);
        course.setDescription("Test Course");

        courseDTO = new CourseDTO();
        courseDTO.setNumCourse(1L);
        courseDTO.setDescription("Test Course DTO");
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);

        List<CourseDTO> courseDTOs = new ArrayList<>();
        courseDTOs.add(courseDTO);

        when(courseServices.retrieveAllCourses()).thenReturn(courses);
        when(courseMapper.toDTO(any(Course.class))).thenReturn(courseDTO);


        ResponseEntity<List<CourseDTO>> response = courseRestController.getAllCourses();


        assertNotNull(response, "La réponse ne doit pas être null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Le statut HTTP doit être OK");
        assertNotNull(response.getBody(), "Le body de la réponse ne doit pas être null");
        assertEquals(1, response.getBody().size(), "La liste des cours retournée doit contenir 1 élément");
        assertEquals(courseDTO, response.getBody().get(0), "Le premier élément de la liste doit correspondre au DTO attendu");


        verify(courseServices, times(1)).retrieveAllCourses();
        verify(courseMapper, times(1)).toDTO(any(Course.class));
    }




    @Test
    void testAddCourse() {
        when(courseMapper.toEntity(any(CourseDTO.class))).thenReturn(course);
        when(courseServices.addCourse(any(Course.class))).thenReturn(course);
        when(courseMapper.toDTO(any(Course.class))).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseRestController.addCourse(courseDTO);

        assertNotNull(response, "La réponse ne doit pas être null");
        assertNotNull(response.getBody(), "Le body de la réponse ne doit pas être null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Le statut HTTP doit être CREATED");
        assertEquals(courseDTO, response.getBody(), "Le DTO retourné ne correspond pas au DTO attendu");

        verify(courseServices, times(1)).addCourse(any(Course.class));
    }

    @Test
    void testGetCourseById() {
        // Préparation des données
        Long courseId = 1L;

        Course course = Course.builder()
                .numCourse(courseId)
                .level(2)
                .typeCourse(TypeCourse.COLLECTIVE_ADULT)
                .price(200.0F)
                .timeSlot(2)
                .build();

        CourseDTO courseDTO = CourseDTO.builder()
                .numCourse(courseId)
                .level(2)
                .typeCourse(TypeCourse.COLLECTIVE_ADULT)
                .price(200.0F)
                .timeSlot(2)
                .build();

        // Simulations
        when(courseServices.retrieveCourse(courseId)).thenReturn(course);
        when(courseMapper.toDTO(any(Course.class))).thenReturn(courseDTO);

        // Exécution du test
        ResponseEntity<CourseDTO> response = courseRestController.getById(courseId);

        // Assertions
        assertNotNull(response, "La réponse ne doit pas être null");
        assertNotNull(response.getBody(), "Le body de la réponse ne doit pas être null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Le statut HTTP doit être OK");
        assertEquals(courseDTO, response.getBody(), "Le DTO retourné ne correspond pas au DTO attendu");

        // Vérifications des appels
        verify(courseServices, times(1)).retrieveCourse(courseId);
        verify(courseMapper, times(1)).toDTO(any(Course.class));
    }






    @Test
    void testUpdateCourse() {
        when(courseMapper.toEntity(any(CourseDTO.class))).thenReturn(course);
        when(courseServices.updateCourse(any(Course.class))).thenReturn(course);
        when(courseMapper.toDTO(any(Course.class))).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseRestController.updateCourse(courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDTO, response.getBody());
        verify(courseServices, times(1)).updateCourse(any(Course.class));
    }



    @Test
    void testDeleteCourse() {
        doNothing().when(courseServices).deleteCourse(1L);

        ResponseEntity<Void> response = courseRestController.deleteCourse(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseServices, times(1)).deleteCourse(1L);
    }
}
