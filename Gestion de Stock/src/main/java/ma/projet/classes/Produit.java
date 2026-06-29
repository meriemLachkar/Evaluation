package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(
        name = "Produit.findByPrixSuperieur100",
        query = "SELECT p FROM Produit p WHERE p.prix > 100"
)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;

    private float prix;


    @ManyToOne
    private Categorie categorie;

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignes;



    public Produit() {
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix +
                '}';
    }
}
