package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionServicesImpl implements ISubscriptionServices {

    private ISubscriptionRepository subscriptionRepository;
    private ISkierRepository skierRepository;

    /**
     * Ajoute une souscription en fonction du type de souscription (mensuelle, semestrielle, annuelle).
     *
     * @param subscription La souscription à ajouter.
     * @return La souscription ajoutée.
     */
    @Override
    public Subscription addSubscription(Subscription subscription) {
        // En fonction du type de souscription, définir la date de fin
        switch (subscription.getTypeSub()) {
            case ANNUAL:
                subscription.setEndDate(subscription.getStartDate().plusYears(1));
                break;
            case SEMESTRIEL:
                subscription.setEndDate(subscription.getStartDate().plusMonths(6));
                break;
            case MONTHLY:
                subscription.setEndDate(subscription.getStartDate().plusMonths(1));
                break;
        }
        // Sauvegarder la souscription dans la base de données
        return subscriptionRepository.save(subscription);
    }

    /**
     * Met à jour une souscription.
     *
     * @param subscription La souscription à mettre à jour.
     * @return La souscription mise à jour.
     */
    @Override
    public Subscription updateSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    /**
     * Récupère une souscription par son ID.
     *
     * @param numSubscription L'ID de la souscription à récupérer.
     * @return La souscription associée à l'ID, ou null si elle n'existe pas.
     */
    @Override
    public Subscription retrieveSubscriptionById(Long numSubscription) {
        return subscriptionRepository.findById(numSubscription).orElse(null);
    }

    /**
     * Récupère toutes les souscriptions d'un type donné.
     *
     * @param type Le type de souscription (mensuelle, semestrielle, annuelle).
     * @return Un ensemble de souscriptions du type spécifié, trié par date de début.
     */
    @Override
    public Set<Subscription> getSubscriptionByType(TypeSubscription type) {
        return subscriptionRepository.findByTypeSubOrderByStartDateAsc(type);
    }

    /**
     * Récupère les souscriptions créées entre deux dates.
     *
     * @param startDate La date de début de la période.
     * @param endDate La date de fin de la période.
     * @return Une liste des souscriptions créées entre ces deux dates.
     */
    @Override
    public List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate) {
        return subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate);
    }

    /**
     * Une tâche planifiée pour récupérer et afficher les souscriptions qui arrivent à leur terme.
     * Cette tâche est exécutée toutes les 30 secondes.
     */
    @Override
    @Scheduled(cron = "*/30 * * * * *") // Tâche qui s'exécute toutes les 30 secondes
    public void retrieveSubscriptions() {
        // Parcours des souscriptions et log des informations associées
        for (Subscription sub : subscriptionRepository.findDistinctOrderByEndDateAsc()) {
            Skier aSkier = skierRepository.findBySubscription(sub);
            log.info(sub.getNumSub().toString() + " | " + sub.getEndDate().toString()
                    + " | " + aSkier.getFirstName() + " " + aSkier.getLastName());
        }
    }

    /**
     * Une tâche planifiée pour calculer et afficher le revenu récurrent mensuel de l'entreprise.
     * Cette tâche est exécutée toutes les 30 secondes.
     */
    @Scheduled(cron = "*/30 * * * * *") // Tâche qui s'exécute toutes les 30 secondes
    public void showMonthlyRecurringRevenue() {
        // Calcul du revenu récurrent mensuel à partir des souscriptions de type mensuel, semestriel et annuel
        Float revenue = subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)
                + subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL) / 6
                + subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL) / 12;
        // Affichage du revenu récurrent mensuel dans les logs
        log.info("Monthly Revenue = " + revenue);
    }
}
