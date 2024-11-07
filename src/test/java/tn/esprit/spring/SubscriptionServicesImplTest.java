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

class SubscriptionServicesImplTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test pour la méthode addSubscription
    @Test
    void testAddSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Configurer le comportement du repository
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // Appeler la méthode
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals(subscription, result);
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    // Test pour la méthode updateSubscription
    @Test
    void testUpdateSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.MONTHLY);

        // Configurer le comportement du repository
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // Appeler la méthode
        Subscription result = subscriptionServices.updateSubscription(subscription);

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals(subscription, result);
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    // Test pour la méthode retrieveSubscriptionById
    @Test
    void testRetrieveSubscriptionById() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Configurer le comportement du repository
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        // Appeler la méthode
        Subscription result = subscriptionServices.retrieveSubscriptionById(1L);

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals(subscription, result);
        verify(subscriptionRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveSubscriptionByIdNotFound() {
        // Configurer le comportement du repository
        when(subscriptionRepository.findById(999L)).thenReturn(Optional.empty());

        // Appeler la méthode
        Subscription result = subscriptionServices.retrieveSubscriptionById(999L);

        // Vérifier les résultats
        assertNull(result);
        verify(subscriptionRepository, times(1)).findById(999L);
    }

    // Test pour la méthode getSubscriptionByType
    @Test
    void testGetSubscriptionByType() {
        Set<Subscription> subscriptions = Set.of(
                new Subscription(1L, LocalDate.now(), LocalDate.now().plusDays(30), 100f, TypeSubscription.ANNUAL),
                new Subscription(2L, LocalDate.now(), LocalDate.now().plusDays(30), 100f, TypeSubscription.ANNUAL)
        );

        // Configurer le comportement du repository
        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL)).thenReturn(subscriptions);

        // Appeler la méthode
        Set<Subscription> result = subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL);

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subscriptionRepository, times(1)).findByTypeSubOrderByStartDateAsc(TypeSubscription.ANNUAL);
    }

    // Test pour la méthode retrieveSubscriptionsByDates
    @Test
    void testRetrieveSubscriptionsByDates() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Configurer le comportement du repository
        when(subscriptionRepository.getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(subscription));

        // Appeler la méthode
        List<Subscription> result = subscriptionServices.retrieveSubscriptionsByDates(LocalDate.now(), LocalDate.now().plusDays(30));

        // Vérifier les résultats
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(subscriptionRepository, times(1)).getSubscriptionsByStartDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    // Test pour la méthode showMonthlyRecurringRevenue (vérification du calcul du revenu mensuel)
    @Test
    void testShowMonthlyRecurringRevenue() {
        // Simuler des valeurs de revenu dans le repository
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(1000f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(2000f);
        when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(3000f);

        // Appeler la méthode
        subscriptionServices.showMonthlyRecurringRevenue();

        // Vérifier les appels du repository (vous ne pouvez pas vérifier le log directement dans ce test)
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
        verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
    }
}
