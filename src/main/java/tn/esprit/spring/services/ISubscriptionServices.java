package tn.esprit.spring.services;

import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ISubscriptionServices {

	/**
	 * Ajoute une nouvelle souscription.
	 *
	 * @param subscription La souscription à ajouter.
	 * @return La souscription ajoutée.
	 */
	Subscription addSubscription(Subscription subscription);

	/**
	 * Récupère une souscription par son ID.
	 *
	 * @param id L'ID de la souscription.
	 * @return La souscription associée à l'ID, ou null si elle n'existe pas.
	 */
	Subscription retrieveSubscriptionById(Long id);

	/**
	 * Récupère toutes les souscriptions d'un type spécifique.
	 *
	 * @param typeSubscription Le type de souscription à récupérer (mensuelle, semestrielle, annuelle).
	 * @return Un ensemble de souscriptions correspondant au type donné.
	 */
	Set<Subscription> getSubscriptionByType(TypeSubscription typeSubscription);

	/**
	 * Met à jour une souscription existante.
	 *
	 * @param subscription La souscription à mettre à jour.
	 * @return La souscription mise à jour.
	 */
	Subscription updateSubscription(Subscription subscription);

	/**
	 * Récupère les souscriptions créées entre deux dates.
	 *
	 * @param startDate La date de début de la période.
	 * @param endDate La date de fin de la période.
	 * @return La liste des souscriptions créées entre les deux dates.
	 */
	List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate);
	public void retrieveSubscriptions();
}
