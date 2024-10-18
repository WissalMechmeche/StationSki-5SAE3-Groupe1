package com.example.gestionstationskii;

import com.example.gestionstationskii.entities.Course;
import com.example.gestionstationskii.entities.Registration;
import com.example.gestionstationskii.entities.Skier;
import com.example.gestionstationskii.repositories.ICourseRepository;
import com.example.gestionstationskii.repositories.IRegistrationRepository;
import com.example.gestionstationskii.repositories.ISkierRepository;
import com.example.gestionstationskii.services.RegistrationServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegistrationServicesImplTest {

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
        // Créer une inscription et un skieur
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        Registration registration = new Registration();
        registration.setNumWeek(2);

        // Simuler la recherche du skieur dans le repository
        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        // Simuler la sauvegarde de l'inscription
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class)))
                .thenReturn(registration);

        // Appeler la méthode testée
        Registration savedRegistration = registrationService.addRegistrationAndAssignToSkier(registration, 1L);

        // Vérifier que l'inscription a bien été assignée au skieur
        Assertions.assertNotNull(savedRegistration, "L'inscription doit être sauvegardée");
        Assertions.assertEquals(skier, savedRegistration.getSkier(), "Le skieur doit être assigné à l'inscription");

        // Vérifier les appels au repository
        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }

    @Test
    public void testAssignRegistrationToCourse() {
        // Créer une inscription et un cours
        Registration registration = new Registration();
        registration.setNumRegistration(1L);

        Course course = new Course();
        course.setNumCourse(1L);

        // Simuler la recherche de l'inscription et du cours
        Mockito.when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Simuler la sauvegarde de l'inscription après l'assignation au cours
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class)))
                .thenReturn(registration);

        // Appeler la méthode testée
        Registration updatedRegistration = registrationService.assignRegistrationToCourse(1L, 1L);

        // Vérifier que l'inscription a bien été assignée au cours
        Assertions.assertNotNull(updatedRegistration, "L'inscription doit être mise à jour");
        Assertions.assertEquals(course, updatedRegistration.getCourse(), "Le cours doit être assigné à l'inscription");

        // Vérifier les appels aux repositories
        verify(registrationRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }
}
