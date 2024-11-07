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

        when(subscriptionServices.addSubscription(any(Subscription.class))).thenReturn(subscription);

        Subscription result = subscriptionRestController.addSubscription(subscription);

        assertNotNull(result);
        assertEquals(subscription, result);
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

        assertNotNull(result);
        assertEquals(subscription, result);
        verify(subscriptionServices, times(1)).retrieveSubscriptionById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(subscriptionServices.retrieveSubscriptionById(999L)).thenReturn(null);

        Subscription result = subscriptionRestController.getById(999L);

        assertNull(result);
        verify(subscriptionServices, times(1)).retrieveSubscriptionById(999L);
    }

    @Test
    void testGetSubscriptionsByType() {
        Set<Subscription> subscriptions = new HashSet<>();
        Subscription sub1 = new Subscription(1L, LocalDate.now(), LocalDate.now().plusDays(30), 99.99f, TypeSubscription.ANNUAL);
        subscriptions.add(sub1);

        when(subscriptionServices.getSubscriptionByType(TypeSubscription.ANNUAL)).thenReturn(subscriptions);

        Set<Subscription> result = subscriptionRestController.getSubscriptionsByType(TypeSubscription.ANNUAL);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(subscriptionServices, times(1)).getSubscriptionByType(TypeSubscription.ANNUAL);
    }

    @Test
    void testGetSubscriptionsByTypeEmpty() {
        Set<Subscription> subscriptions = new HashSet<>();
        when(subscriptionServices.getSubscriptionByType(TypeSubscription.MONTHLY)).thenReturn(subscriptions);

        Set<Subscription> result = subscriptionRestController.getSubscriptionsByType(TypeSubscription.MONTHLY);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(subscriptionServices, times(1)).getSubscriptionByType(TypeSubscription.MONTHLY);
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

        assertNotNull(result);
        assertEquals(subscription, result);
        verify(subscriptionServices, times(1)).updateSubscription(subscription);
    }

    @Test
    void testUpdateSubscriptionWithInvalidData() {
        Subscription invalidSubscription = new Subscription();
        invalidSubscription.setNumSub(1L);
        invalidSubscription.setStartDate(LocalDate.now().plusDays(1)); // start date in the future
        invalidSubscription.setEndDate(LocalDate.now()); // end date in the past
        invalidSubscription.setPrice(-50f); // invalid price
        invalidSubscription.setTypeSub(TypeSubscription.MONTHLY);

        when(subscriptionServices.updateSubscription(any(Subscription.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            subscriptionRestController.updateSubscription(invalidSubscription);
        });

        verify(subscriptionServices, times(1)).updateSubscription(invalidSubscription);
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

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(subscription, result.get(0));
        verify(subscriptionServices, times(1)).retrieveSubscriptionsByDates(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void testGetSubscriptionsByDatesEmpty() {
        List<Subscription> subscriptions = List.of();
        when(subscriptionServices.retrieveSubscriptionsByDates(any(LocalDate.class), any(LocalDate.class))).thenReturn(subscriptions);

        List<Subscription> result = subscriptionRestController.getSubscriptionsByDates(LocalDate.now(), LocalDate.now().plusDays(30));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(subscriptionServices, times(1)).retrieveSubscriptionsByDates(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void testAddSubscriptionWithInvalidData() {
        Subscription invalidSubscription = new Subscription();
        invalidSubscription.setNumSub(1L);
        invalidSubscription.setStartDate(LocalDate.now());
        invalidSubscription.setEndDate(LocalDate.now().minusDays(1)); // Date de fin avant la date de début
        invalidSubscription.setPrice(-50f); // Prix invalide (négatif)
        invalidSubscription.setTypeSub(TypeSubscription.MONTHLY);

        when(subscriptionServices.addSubscription(any(Subscription.class))).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            subscriptionRestController.addSubscription(invalidSubscription);
        });

        verify(subscriptionServices, times(1)).addSubscription(invalidSubscription);
    }
}