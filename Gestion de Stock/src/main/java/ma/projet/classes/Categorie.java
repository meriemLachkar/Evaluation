package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String libelle;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.EAGER)
    private List<Produit> produits;

    public Categorie(String nom, String libelle) {
        this.nom = nom;
        this.libelle = libelle;
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getLibelle() {

        return libelle;
    }

    public void setLibelle(String libelle) {

        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
