package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class MariageService implements IDao<Mariage> {

    private EntityManagerFactory emf = HibernateUtil.getEntityManagerFactory();

    @Override
    public boolean create(Mariage o) {
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
    public boolean update(Mariage o) {
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
    public boolean delete(Mariage o) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Mariage managed = em.find(Mariage.class, o.getId());
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
            em.createQuery("DELETE FROM Mariage").executeUpdate();
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
    public Mariage findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Mariage.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mariage> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Mariage m", Mariage.class).getResultList();
        } finally {
            em.close();
        }
    }

    public long getNombreHommesMariesAQuatreFemmes(Date dateDebut, Date dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Homme> hommeRoot = cq.from(Homme.class);
            Join<Homme, Mariage> mariageJoin = hommeRoot.join("mariages");

            cq.select(cb.countDistinct(hommeRoot))
                    .where(
                            cb.greaterThanOrEqualTo(mariageJoin.<Date>get("dateDebut"), dateDebut),
                            cb.or(
                                    cb.isNull(mariageJoin.<Date>get("dateFin")),
                                    cb.lessThanOrEqualTo(mariageJoin.<Date>get("dateFin"), dateFin)
                            )
                    )
                    .groupBy(hommeRoot.get("id"))
                    .having(cb.equal(cb.count(mariageJoin), 4L));

            List<Long> result = em.createQuery(cq).getResultList();
            return result.size();
        } finally {
            em.close();
        }
    }

    public List<Homme> getHommesMariesAQuatreFemmes(Date dateDebut, Date dateFin) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Homme> query = em.createQuery(
                    "SELECT m.homme FROM Mariage m " +
                            "WHERE m.dateDebut >= :dateDebut " +
                            "AND (m.dateFin IS NULL OR m.dateFin <= :dateFin) " +
                            "GROUP BY m.homme " +
                            "HAVING COUNT(m.femme) = 4",
                    Homme.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
