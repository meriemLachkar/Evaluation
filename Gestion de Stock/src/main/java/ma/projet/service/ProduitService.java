package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.AbstractIDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class ProduitService extends AbstractIDao<Produit, Integer> {

    public ProduitService(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Produit> findByCategorie(int categorieId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT p FROM Produit p WHERE p.categorie.id = :catId",
                    Produit.class
            );
            query.setParameter("catId", categorieId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> findProduitsCommandesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                            "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
                    Produit.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> findProduitsByCommande(int commandeId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                    "SELECT lcp.produit FROM LigneCommandeProduit lcp " +
                            "WHERE lcp.commande.id = :cmdId",
                    Produit.class
            );
            query.setParameter("cmdId", commandeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> findProduitsChers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Produit> query = em.createNamedQuery(
                    "Produit.findByPrixSuperieur100",
                    Produit.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
