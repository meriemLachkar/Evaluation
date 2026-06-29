package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.AbstractIDao;

import javax.persistence.EntityManagerFactory;


public class CommandeService extends AbstractIDao<Commande,Integer> {
    public CommandeService(EntityManagerFactory emf) {

        super(emf);
    }
}
