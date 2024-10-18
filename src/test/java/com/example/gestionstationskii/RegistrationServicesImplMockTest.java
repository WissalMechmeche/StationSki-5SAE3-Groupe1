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
public class RegistrationServicesImplMockTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        // Créer un skieur simulé
        Skier skier = new Skier();
        skier.setNumSkier(1L);

        // Créer une inscription simulée
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registration.setNumWeek(3);

        // Simuler le comportement du repository skier
        Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class)))
                .thenReturn(registration);

        // Appeler la méthode à tester
        Registration savedRegistration = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Vérifications
        Assertions.assertNotNull(savedRegistration);
        Assertions.assertEquals(1L, savedRegistration.getNumRegistration());
        Assertions.assertEquals(skier, savedRegistration.getSkier());

        // Vérifier que les méthodes appropriées ont été appelées
        verify(skierRepository).findById(1L);
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }

    @Test
    public void testAssignRegistrationToCourse() {
        // Créer une inscription simulée
        Registration registration = new Registration();
        registration.setNumRegistration(1L);

        // Créer un cours simulé
        Course course = new Course();
        course.setNumCourse(1L);

        // Simuler le comportement du repository
        Mockito.when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(registrationRepository.save(Mockito.any(Registration.class)))
                .thenReturn(registration);

        // Appeler la méthode à tester
        Registration savedRegistration = registrationServices.assignRegistrationToCourse(1L, 1L);

        // Vérifications
        Assertions.assertNotNull(savedRegistration);
        Assertions.assertEquals(course, savedRegistration.getCourse());

        // Vérifier que les méthodes appropriées ont été appelées
        verify(registrationRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(registrationRepository).save(Mockito.any(Registration.class));
    }
}
