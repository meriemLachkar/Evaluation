package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.AbstractIDao;

import javax.persistence.EntityManagerFactory;


public class CategorieService extends AbstractIDao<Categorie, Integer> {

    public CategorieService(EntityManagerFactory emf) {

        super(emf);
    }

}
