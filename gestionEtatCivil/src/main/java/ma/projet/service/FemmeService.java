package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    private EntityManagerFactory emf = HibernateUtil.getEntityManagerFactory();

    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Femme managed = em.find(Femme.class, o.getId());
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
            em.createQuery("DELETE FROM Femme").executeUpdate();
            em.createQuery("DELETE FROM Personne p WHERE TYPE(p) = Femme").executeUpdate();
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
    public Femme findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Femme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Femme f", Femme.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Femme getFemmeLaPlusAgee() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Femme> query = em.createQuery(
                    "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC",
                    Femme.class
            );
            query.setMaxResults(1);
            List<Femme> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public int getNombreEnfantsEntreDeuxDates(Femme femme, Date dateDebut, Date dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Femme.nombreEnfantsEntreDeuxDates");
            query.setParameter("femmeId", femme.getId());
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            Number result = (Number) query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }

    public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Femme> query = em.createQuery(
                    "SELECT DISTINCT f FROM Femme f JOIN FETCH f.mariages " +
                            "WHERE SIZE(f.mariages) >= 2",
                    Femme.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
