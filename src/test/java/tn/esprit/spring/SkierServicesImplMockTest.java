package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SkierServicesImplMockTest {

  @InjectMocks
  private SkierServicesImpl skierService; // Injecter le service à tester

  @Mock
  private ISkierRepository skierRepository;
  @Mock
  private IPisteRepository pisteRepository; // Mock du repository pour Skier

  @Mock
  private ISubscriptionRepository subscriptionRepository; // Mock du repository pour Subscription

  // Test pour ajouter un skieur
  @Test
  @Order(1)
  public void testAddSkier() {
    // Créer une souscription
    Subscription subscription = new Subscription();
    subscription.setTypeSub(TypeSubscription.ANNUAL);
    subscription.setStartDate(LocalDate.now());

    // Créer un skieur
    Skier skier = new Skier();
    skier.setFirstName("John");
    skier.setLastName("Doe");
    skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
    skier.setCity("New York");
    skier.setSubscription(subscription);

    // Simuler le comportement du repository pour save
    Mockito.when(skierRepository.save(Mockito.any(Skier.class))).thenReturn(skier);

    // Appeler la méthode addSkier du service
    Skier savedSkier = skierService.addSkier(skier);

    // Vérifier que les informations sont correctes
    Assertions.assertNotNull(savedSkier, "Le skieur ne doit pas être nul");
    Assertions.assertEquals("John", savedSkier.getFirstName(), "Le prénom doit être John");
    Assertions.assertEquals("Doe", savedSkier.getLastName(), "Le nom doit être Doe");
    Assertions.assertEquals(LocalDate.now().plusYears(1), savedSkier.getSubscription().getEndDate(),
      "La date de fin de la souscription doit être d'un an plus tard");

    // Vérifier que le repository a bien été appelé
    verify(skierRepository).save(Mockito.any(Skier.class));
  }

  // Test pour assigner un skieur à une souscription
  @Test
  @Order(2)
  public void testAssignSkierToSubscription() {
    // Créer un skieur et une souscription
    Skier skier = new Skier();
    skier.setNumSkier(1L);
    Subscription subscription = new Subscription();
    subscription.setNumSub(2L);

    // Simuler le comportement du repository
    Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
    Mockito.when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));
    Mockito.when(skierRepository.save(Mockito.any(Skier.class))).thenReturn(skier);

    // Appeler la méthode du service pour assigner la souscription
    Skier updatedSkier = skierService.assignSkierToSubscription(1L, 2L);

    // Vérifier que la souscription a été correctement assignée
    Assertions.assertNotNull(updatedSkier, "Le skieur ne doit pas être nul");
    Assertions.assertEquals(subscription, updatedSkier.getSubscription(), "La souscription doit être assignée");

    // Vérifier que les méthodes du repository ont été appelées
    verify(skierRepository).findById(1L);
    verify(subscriptionRepository).findById(2L);
    verify(skierRepository).save(Mockito.any(Skier.class));
  }

  // Test pour vérifier la récupération de tous les skieurs
  @Test
  @Order(3)
  public void testRetrieveAllSkiers() {
    // Appeler la méthode retrieveAllSkiers du service
    skierService.retrieveAllSkiers();

    // Vérifier que la méthode findAll du repository a été appelée une fois
    verify(skierRepository).findAll();
  }

  // Test pour assigner un skieur à une piste
  @Test
  @Order(4)
  public void testAssignSkierToPiste() {
    // Créer un skieur et une piste
    Skier skier = new Skier();
    skier.setNumSkier(1L);
    Piste piste = new Piste();
    piste.setNumPiste(3L);

    // Simuler le comportement du repository
    Mockito.when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
    Mockito.when(pisteRepository.findById(3L)).thenReturn(Optional.of(piste));
    Mockito.when(skierRepository.save(Mockito.any(Skier.class))).thenReturn(skier);

    // Appeler la méthode du service pour assigner la piste
    Skier updatedSkier = skierService.assignSkierToPiste(1L, 3L);

    // Vérifier que la piste a été correctement assignée
    Assertions.assertNotNull(updatedSkier, "Le skieur ne doit pas être nul");
    Assertions.assertTrue(updatedSkier.getPistes().contains(piste), "La piste doit être assignée au skieur");

    // Vérifier que les méthodes du repository ont été appelées
    verify(skierRepository).findById(1L);
    verify(pisteRepository).findById(3L);
    verify(skierRepository).save(Mockito.any(Skier.class));
  }
}
