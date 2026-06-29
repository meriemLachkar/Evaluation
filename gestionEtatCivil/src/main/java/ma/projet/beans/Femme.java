package ma.projet.beans;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "femme")
@NamedQueries({
        @NamedQuery(
                name = "Femme.femmesMarieesDeuxFoisOuPlus",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Femme.nombreEnfantsEntreDeuxDates",
                query = "SELECT SUM(m.nbrEnfant) FROM mariage m " +
                        "WHERE m.femme_id = :femmeId " +
                        "AND m.dateDebut >= :dateDebut " +
                        "AND (m.dateFin IS NULL OR m.dateFin <= :dateFin)"
        )
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
