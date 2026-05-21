package com.mycompany.hibernate_project.service;

import com.mycompany.hibernate_project.model.Produit;
import com.mycompany.hibernate_project.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProduitService {

    // 1. CREATE : Ajouter un nouveau produit
    public void ajouterProduit(Produit p) {
        Transaction tx = null;
        // On demande un "chariot" (une session) à notre usine
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction(); // On commence la transaction
            session.persist(p);              // On dit à Hibernate de sauvegarder l'objet
            tx.commit();                     // On valide l'enregistrementle ProduitServvi
        } catch (Exception e) {
            if (tx != null) tx.rollback();   // En cas d'erreur, on annule tout pour protéger la base
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    // 2. READ : Récupérer tous les produits (pour les afficher dans le tableau)
    public List<Produit> getAllProduits() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Ici, on utilise HQL (Hibernate Query Language). 
            // On sélectionne la classe Java 'Produit', et non la table SQL !
            return session.createQuery("from Produit", Produit.class).list();
        }
        // Pas besoin de transaction ici car on ne fait que lire, on ne modifie rien.
    }

    // 3. UPDATE : Mettre à jour un produit existant
    public void modifierProduit(Produit p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(p);                // 'merge' met à jour la ligne correspondante dans la base
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur : " + e.getMessage());
        }
        
    }

    // 4. DELETE : Supprimer un produit grâce à son ID
    public void supprimerProduit(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            
            // On va d'abord chercher le produit dans la base avec son ID
            Produit p = session.get(Produit.class, id);
            
            // S'il existe bien, on le supprime
            if (p != null) {
                session.remove(p);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}