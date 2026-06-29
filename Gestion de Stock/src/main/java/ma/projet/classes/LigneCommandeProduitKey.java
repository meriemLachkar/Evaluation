package ma.projet.classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitKey implements Serializable {

    @Column(name = "produit_id")
    private int produit;

    @Column(name = "commande_id")
    private int commande;

    public LigneCommandeProduitKey() {
    }

    public LigneCommandeProduitKey(int produit, int commande) {
        this.produit = produit;
        this.commande = commande;
    }

    public int getProduit() {
        return produit;
    }

    public int getCommande() {
        return commande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitKey)) return false;
        LigneCommandeProduitKey that = (LigneCommandeProduitKey) o;
        return produit == that.produit && commande == that.commande;
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit, commande);
    }
}

