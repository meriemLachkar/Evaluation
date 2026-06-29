package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @OneToMany(mappedBy = "commande", fetch = FetchType.EAGER)
    private List<LigneCommandeProduit> lignes;

    public Commande(LocalDate date) {
        this.date = date;
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LigneCommandeProduit> getLignes() {

        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
        StringBuilder sb = new StringBuilder();
        sb.append("Commande : ").append(id)
                .append("     Date : ").append(date.format(formatter)).append("\n");
        sb.append("Liste des produits :\n");
        sb.append(String.format("%-15s %-12s %s%n", "Référence", "Prix", "Quantité"));
        if (lignes != null) {
            for (LigneCommandeProduit ligne : lignes) {
                sb.append(String.format("%-15s %-12s %d%n",
                        ligne.getProduit().getReference(),
                        (int) ligne.getProduit().getPrix() + " DH",
                        ligne.getQuantite()));
            }
        }
        return sb.toString();
    }
}
