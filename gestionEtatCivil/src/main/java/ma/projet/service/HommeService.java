package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    private EntityManagerFactory emf = HibernateUtil.getEntityManagerFactory();

    @Override
    public boolean create(Homme o) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Homme o) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Homme o) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Homme managed = em.find(Homme.class, o.getId());
            if (managed != null) em.remove(managed);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteAll() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Homme").executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Homme findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Homme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT h FROM Homme h", Homme.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Femme> getEpousesEntreDeuxDates(Homme homme, Date dateDebut, Date dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Femme> query = em.createQuery(
                    "SELECT m.femme FROM Mariage m " +
                            "WHERE m.homme.id = :hommeId " +
                            "AND m.dateDebut >= :dateDebut " +
                            "AND (m.dateFin IS NULL OR m.dateFin <= :dateFin)",
                    Femme.class
            );
            query.setParameter("hommeId", homme.getId());
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Mariage> getMariagesDetailsParHomme(Homme homme) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Mariage> query = em.createQuery(
                    "SELECT m FROM Mariage m " +
                            "JOIN FETCH m.femme " +
                            "WHERE m.homme.id = :hommeId " +
                            "ORDER BY m.dateDebut ASC",
                    Mariage.class
            );
            query.setParameter("hommeId", homme.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
