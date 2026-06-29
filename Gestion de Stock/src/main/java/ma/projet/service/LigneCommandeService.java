package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.LigneCommandeProduitKey;
import ma.projet.dao.AbstractIDao;

import javax.persistence.EntityManagerFactory;


public class LigneCommandeService extends AbstractIDao<LigneCommandeProduit, LigneCommandeProduitKey> {
    public LigneCommandeService(EntityManagerFactory emf) {
        super(emf);
    }
}

