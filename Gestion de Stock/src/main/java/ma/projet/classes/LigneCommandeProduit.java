package ma.projet.classes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class LigneCommandeProduit {
    private int quantite;

    @EmbeddedId
    private LigneCommandeProduitKey id;
    @JoinColumn(name = "commande_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Commande commande;
    @JoinColumn(name = "produit_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Produit produit;


    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.id = new LigneCommandeProduitKey(
                produit.getId(),
                commande.getId());
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    public Commande getCommande() {
        return commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        LigneCommandeProduit that = (LigneCommandeProduit) object;
        return quantite == that.quantite && java.util.Objects.equals(id, that.id) && java.util.Objects.equals(commande, that.commande) && java.util.Objects.equals(produit, that.produit);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), quantite, id, commande, produit);
    }

    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "quantite=" + quantite +
                '}';
    }
}
