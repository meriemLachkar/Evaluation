package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService implements IDao<Employe> {
    @Override
    public boolean create(Employe emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(emp);
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
    public boolean update(Employe emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(emp);
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
    public boolean delete(Employe emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(emp);
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
    public Employe findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        }
    }

    @Override
    public List<Employe> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employe", Employe.class).list();
        }
    }

    public List<EmployeTache> getTachesRealiseesByEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EmployeTache> query = session.createQuery(
                    "FROM EmployeTache et WHERE et.employe.id = :employeId " +
                            "ORDER BY et.dateDebutReelle",
                    EmployeTache.class
            );
            query.setParameter("employeId", employeId);
            List<EmployeTache> liste = query.list();

            Employe employe = session.get(Employe.class, employeId);
            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("  Tâches réalisées par : " +
                    (employe != null ? employe.getNom() + " " + employe.getPrenom() : "id=" + employeId));
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

    public List<Projet> getProjetsGeresByEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Projet> query = session.createQuery(
                    "FROM Projet p WHERE p.chefProjet.id = :employeId ORDER BY p.dateDebut",
                    Projet.class
            );
            query.setParameter("employeId", employeId);
            List<Projet> projets = query.list();

            Employe employe = session.get(Employe.class, employeId);
            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("  Projets gérés par : " +
                    (employe != null ? employe.getNom() + " " + employe.getPrenom() : "id=" + employeId));
            System.out.println("══════════════════════════════════════════════════════");
            System.out.printf("  %-5s %-25s %-15s %-15s%n",
                    "Id", "Nom", "Date Début", "Date Fin");
            System.out.println("  ─────────────────────────────────────────────────────");
            for (Projet p : projets) {
                System.out.printf("  %-5d %-25s %-15s %-15s%n",
                        p.getId(), p.getNom(), p.getDateDebut(), p.getDateFin());
            }
            System.out.println("══════════════════════════════════════════════════════\n");
            return projets;
        }
    }
}
