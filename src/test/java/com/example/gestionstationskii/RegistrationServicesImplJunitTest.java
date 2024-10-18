package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.*;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.repositories.IRegistrationRepository;
import com.example.gestionstationskii.repositories.ISkierRepository;
import com.example.gestionstationskii.services.RegistrationServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class RegistrationServicesImplJunitTest {

    @InjectMocks
    private RegistrationServicesImpl registrationService;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        // Créer un Skier mock
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1)); // Par exemple, 24 ans

        // Créer une Registration mock
        Registration registration = new Registration();
        registration.setNumWeek(1);

        // Simuler le comportement du repository pour le Skier
        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        // Simuler le comportement du repository pour la Registration
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        // Appeler la méthode de service
        Registration result = registrationService.addRegistrationAndAssignToSkier(registration, 1L);

        // Vérifier que l'inscription a été assignée au Skier
        Assertions.assertNotNull(result);
        Assertions.assertEquals(skier, result.getSkier(), "Le Skier doit être assigné à l'inscription");

        // Vérifier que la méthode save a été appelée
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }

    @Test
    public void testAssignRegistrationToCourse() {
        // Créer une Registration mock
        Registration registration = new Registration();
        registration.setNumRegistration(1L);

        // Créer une Course mock
        Course course = new Course();
        course.setNumCourse(1L);

        // Simuler le comportement du repository pour la Registration et la Course
        Mockito.when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);

        // Appeler la méthode de service
        Registration result = registrationService.assignRegistrationToCourse(1L, 1L);

        // Vérifier que l'inscription a été assignée à la Course
        Assertions.assertNotNull(result);
        Assertions.assertEquals(course, result.getCourse(), "La Course doit être assignée à l'inscription");

        // Vérifier que la méthode save a été appelée
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }

   /* @Test
    public void testAddRegistrationAndAssignToSkierAndCourse() {
        // Créer un Skier mock
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.of(2005, 1, 1)); // Un enfant

        // Créer une Course mock
        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN); // Enfant

        // Créer une Registration mock
        Registration registration = new Registration();
        registration.setNumWeek(1);

        // Simuler le comportement des repositories
        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class))).thenReturn(registration);
        Mockito.when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(Mockito.anyInt(), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(0L); // Pas encore enregistré pour cette semaine


        // Appeler la méthode de service
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Vérifier que l'inscription a été correctement ajoutée
        Assertions.assertNotNull(result);
        Assertions.assertEquals(skier, result.getSkier(), "Le Skier doit être assigné à l'inscription");
        Assertions.assertEquals(course, result.getCourse(), "La Course doit être assignée à l'inscription");

        // Vérifier que la méthode save a été appelée
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }
*/
    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse_FullCourse() {
        // Créer un Skier mock
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1)); // Adulte

        // Créer une Course mock
        Course course = new Course();
        course.setNumCourse(1L);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN); // Enfant

        // Créer une Registration mock
        Registration registration = new Registration();
        registration.setNumWeek(1);

        // Simuler le comportement des repositories
        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(Mockito.anyInt(), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(1L); // Déjà enregistré pour cette semaine

        // Appeler la méthode de service
        Registration result = registrationService.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 1L);

        // Vérifier que l'inscription n'a pas été ajoutée
        Assertions.assertNull(result, "L'inscription ne devrait pas être ajoutée car le cours est complet");
    }

    // Ajoutez d'autres tests selon vos besoins...
}
