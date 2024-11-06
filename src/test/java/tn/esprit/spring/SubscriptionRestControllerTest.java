package tn.esprit.spring;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.controllers.SubscriptionRestController;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISubscriptionServices;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("resource")
class SubscriptionRestControllerTest {

    @Mock
    private ISubscriptionServices subscriptionServices;

    @InjectMocks
    private SubscriptionRestController subscriptionRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setPrice(99.99f);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // Simulez le comportement du service
        when(subscriptionServices.addSubscription(any(Subscription.class))).thenReturn(subscription);

        // Appelez la méthode du contrôleur
        Subscription result = subscriptionRestController.addSubscription(subscription);

        // Vérifiez que le résultat est correct
        assertEquals(subscription, result);
        // Vérifiez que la méthode du service a été appelée
        verify(subscriptionServices, times(1)).addSubscription(subscription);
    }

    @Test
    void testGetById() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setPrice(99.99f);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        when(subscriptionServices.retrieveSubscriptionById(1L)).thenReturn(subscription);

        Subscription result = subscriptionRestController.getById(1L);

        assertEquals(subscription, result);
        verify(subscriptionServices, times(1)).retrieveSubscriptionById(1L);
    }

    @Test
    void testGetSubscriptionsByType() {
        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(new Subscription(1L, LocalDate.now(), LocalDate.now().plusDays(30), 99.99f, TypeSubscription.ANNUAL));
        when(subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL)).thenReturn(subscriptions);

        Set<Subscription> result = subscriptionRestController.getSubscriptionsByType(TypeSubscription.ANNUAL);

        assertEquals(1, result.size());
        verify(subscriptionServices, times(1)).getSubscriptionByType(TypeSubscription.ANNUAL);
    }

    @Test
    void testUpdateSubscription() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setPrice(99.99f);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        when(subscriptionServices.updateSubscription(any(Subscription.class))).thenReturn(subscription);

        Subscription result = subscriptionRestController.updateSubscription(subscription);

        assertEquals(subscription, result);
        verify(subscriptionServices, times(1)).updateSubscription(subscription);
    }

    @Test
    void testGetSubscriptionsByDates() {
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setPrice(99.99f);
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        List<Subscription> subscriptions = List.of(subscription);
        when(subscriptionServices.retrieveSubscriptionsByDates(any(LocalDate.class), any(LocalDate.class))).thenReturn(subscriptions);

        List<Subscription> result = subscriptionRestController.getSubscriptionsByDates(LocalDate.now(), LocalDate.now().plusDays(30));

        assertEquals(1, result.size());
        assertEquals(subscription, result.get(0));
        verify(subscriptionServices, times(1)).retrieveSubscriptionsByDates(any(LocalDate.class), any(LocalDate.class));
    }
}
