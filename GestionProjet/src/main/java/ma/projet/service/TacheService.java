package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    @Override
    public boolean create(Tache tache) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(tache);
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
    public boolean update(Tache tache) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(tache);
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
    public boolean delete(Tache tache) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(tache);
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
    public Tache findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Tache.class, id);
        }
    }

    @Override
    public List<Tache> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Tache", Tache.class).list();
        }
    }

    public List<Tache> getTachesPrixSuperieur1000() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> taches = session
                    .createNamedQuery("Tache.prixSuperieurA1000", Tache.class)
                    .list();

            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("  Tâches dont le prix > 1000 DH");
            System.out.println("══════════════════════════════════════════════════════");
            System.out.printf("  %-5s %-25s %-10s%n", "Id", "Nom", "Prix (DH)");
            System.out.println("  ─────────────────────────────────────────────────────");
            for (Tache t : taches) {
                System.out.printf("  %-5d %-25s %-10.2f%n",
                        t.getId(), t.getNom(), t.getPrix());
            }
            System.out.println("══════════════════════════════════════════════════════\n");
            return taches;
        }
    }

    public List<EmployeTache> getTachesEntreDeuxDates(Date dateDebut, Date dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> liste = session
                    .createNamedQuery("Tache.entreDeuxDates", EmployeTache.class)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .list();

            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.printf("  Tâches réalisées entre %s et %s%n", dateDebut, dateFin);
            System.out.println("══════════════════════════════════════════════════════");
            System.out.printf("  %-5s %-20s %-20s %-20s%n",
                    "Id", "Tâche", "Début Réel", "Fin Réelle");
            System.out.println("  ─────────────────────────────────────────────────────────────────");
            for (EmployeTache et : liste) {
                System.out.printf("  %-5d %-20s %-20s %-20s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        et.getDateDebutReelle(),
                        et.getDateFinReelle());
            }
            System.out.println("══════════════════════════════════════════════════════\n");
            return liste;
        }
    }
}
