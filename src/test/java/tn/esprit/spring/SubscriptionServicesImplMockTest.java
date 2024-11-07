package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class SubscriptionServicesImplMockTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;  // Mock de la dépendance ISubscriptionRepository

    @Mock
    private ISkierRepository skierRepository;  // Mock de la dépendance ISkierRepository

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;  // Service à tester

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialiser les mocks avant chaque test
    }

    // Test unitaire pour la méthode addSubscription
    @Test
    void testAddSubscription() {
        // Création d'une souscription
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);  // Type annuel

        // Mock du comportement de save() dans le repository
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // Appel du service
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Vérifications : la souscription doit être retournée et être égale à celle envoyée
        assertNotNull(result);
        assertEquals(subscription, result);

        // Vérifier que la méthode save a été appelée une fois
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    // Test unitaire pour la méthode updateSubscription
    @Test
    void testUpdateSubscription() {
        // Création d'une souscription
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.MONTHLY);

        // Mock du comportement de save() dans le repository
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // Appel du service
        Subscription result = subscriptionServices.updateSubscription(subscription);

        // Vérifications : la souscription doit être retournée et être égale à celle envoyée
        assertNotNull(result);
        assertEquals(subscription, result);

        // Vérifier que la méthode save a été appelée une fois
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    // Test unitaire pour la méthode retrieveSubscriptionById (cas où l'ID est trouvé)
    @Test
    void testRetrieveSubscriptionById() {
        // Création d'une souscription
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Mock du comportement de findById() dans le repository
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        // Appel du service
        Subscription result = subscriptionServices.retrieveSubscriptionById(1L);

        // Vérifications : la souscription doit être trouvée et égale à celle donnée
        assertNotNull(result);
        assertEquals(subscription, result);

        // Vérifier que la méthode findById a été appelée une fois
        verify(subscriptionRepository, times(1)).findById(1L);
    }

    // Test unitaire pour la méthode retrieveSubscriptionById (cas où l'ID n'est pas trouvé)
    @Test
    void testRetrieveSubscriptionByIdNotFound() {
        // Mock du comportement de findById() dans le repository (aucune souscription trouvée)
        when(subscriptionRepository.findById(999L)).thenReturn(Optional.empty());

        // Appel du service
        Subscription result = subscriptionServices.retrieveSubscriptionById(999L);

        // Vérifications : le résultat doit être null
        assertNull(result);

        // Vérifier que la méthode findById a été appelée une fois
        verify(subscriptionRepository, times(1)).findById(999L);
    }

    // Test unitaire pour la méthode getSubscriptionByType
    @Test
    void testGetSubscriptionByType() {
        Set<Subscription> subscriptions = Set.of(
                new Subscription(1L, LocalDate.now(), LocalDate.now().plusDays(30), 100f, TypeSubscription.ANNUAL),
                new Subscription(2L, LocalDate.now(), LocalDate.now().plusDays(30), 100f, TypeSubscription.ANNUAL)
        );

        // Mock du comportement de findByTypeSubOrderByStartDateAsc() dans le repository
        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL)).thenReturn(subscriptions);

        // Appel du service
        Set<Subscription> result = subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL);

        // Vérifications : les souscriptions retournées doivent être correctes et de la bonne taille
        assertNotNull(result);
        assertEquals(2, result.size());

        // Vérifier que la méthode findByTypeSubOrderByStartDateAsc a été appelée une fois
        verify(subscriptionRepository, times(1)).findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL);
    }

    // Test unitaire pour la méthode retrieveSubscriptionsByDates
    @Test
    void testRetrieveSubscriptionsByDates() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Mock du comportement de getSubscriptionsByStartDateBetween() dans le repository
        when(subscriptionRepository.getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(subscription));

        // Appel du service
        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(LocalDate.now(), LocalDate.now().plusDays(30));

        // Vérifications : la liste des souscriptions retournée doit contenir 1 élément
        assertNotNull(result);
        assertEquals(1, result.size());

        // Vérifier que la méthode getSubscriptionsByStartDateBetween a été appelée une fois
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    // Test unitaire pour la méthode showMonthlyRecurringRevenue (calcul du revenu récurrent)
    @Test
    void testShowMonthlyRecurringRevenue() {
        // Simuler des valeurs de revenu dans le repository pour différents types de souscription
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(1000f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(2000f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(3000f);

        // Appel du service (affichage dans le log)
        subscriptionServices.showMonthlyRecurringRevenue();

        // Vérifier que les méthodes du repository ont été appelées
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
    }
}
