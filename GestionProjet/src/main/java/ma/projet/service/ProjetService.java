package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjetService implements IDao<Projet> {
    @Override
    public boolean create(Projet projet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(projet);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Projet projet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(projet);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Projet projet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(projet);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Projet findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Projet.class, id);
        }
    }

    @Override
    public List<Projet> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Projet", Projet.class).list();
        }
    }

    public List<Tache> getTachesPlanifieesParProjet(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.createQuery(
                    "FROM Tache t WHERE t.projet.id = :projetId ORDER BY t.dateDebut",
                    Tache.class
            );
            query.setParameter("projetId", projetId);
            List<Tache> taches = query.list();

            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("  Liste des tâches planifiées pour le projet id=" + projetId);
            System.out.println("══════════════════════════════════════════════════════");
            System.out.printf("  %-5s %-20s %-15s %-15s %-10s%n",
                    "Id", "Nom", "Date Début", "Date Fin", "Prix (DH)");
            System.out.println("  ─────────────────────────────────────────────────────");
            for (Tache t : taches) {
                System.out.printf("  %-5d %-20s %-15s %-15s %-10.2f%n",
                        t.getId(), t.getNom(), t.getDateDebut(), t.getDateFin(), t.getPrix());
            }
            System.out.println("══════════════════════════════════════════════════════\n");
            return taches;
        }
    }

    public List<EmployeTache> getTachesRealiseesPourProjet(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EmployeTache> query = session.createQuery(
                    "FROM EmployeTache et WHERE et.tache.projet.id = :projetId " +
                            "ORDER BY et.dateDebutReelle",
                    EmployeTache.class
            );
            query.setParameter("projetId", projetId);
            List<EmployeTache> liste = query.list();

            // Récupérer le projet pour l'affichage
            Projet projet = session.get(Projet.class, projetId);

            System.out.println();
            if (projet != null) {
                System.out.printf("  Projet : %-5d  Nom : %-25s  Date début : %s%n",
                        projet.getId(), projet.getNom(), projet.getDateDebut());
            }
            System.out.println("  Liste des tâches réalisées:");
            System.out.printf("  %-5s %-20s %-20s %-20s%n",
                    "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            System.out.println("  ─────────────────────────────────────────────────────────────────");
            for (EmployeTache et : liste) {
                System.out.printf("  %-5d %-20s %-20s %-20s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        et.getDateDebutReelle(),
                        et.getDateFinReelle());
            }
            System.out.println();
            return liste;
        }
    }
}
