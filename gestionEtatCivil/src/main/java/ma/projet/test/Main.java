package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws ParseException {

        FemmeService   femmeService   = new FemmeService();
        HommeService   hommeService   = new HommeService();
        MariageService mariageService = new MariageService();

        mariageService.deleteAll();
        femmeService.deleteAll();
        hommeService.deleteAll();

        System.out.println("\n=== Création de 10 femmes ===");
        Femme[] femmes = {
                new Femme("RAMI",    "Salima",  "0612000001", "Marrakech",  sdf.parse("15/03/1970")),
                new Femme("ALI",     "Amal",    "0612000002", "Casablanca", sdf.parse("22/07/1975")),
                new Femme("ALAOUI",  "Wafa",    "0612000003", "Rabat",      sdf.parse("10/01/1980")),
                new Femme("ALAMI",   "Karima",  "0612000004", "Fès",        sdf.parse("05/09/1968")),
                new Femme("BAHI",    "Nadia",   "0612000005", "Agadir",     sdf.parse("30/11/1985")),
                new Femme("TAZI",    "Hajar",   "0612000006", "Meknès",     sdf.parse("18/04/1990")),
                new Femme("IDRISSI", "Sara",    "0612000007", "Oujda",      sdf.parse("25/06/1988")),
                new Femme("FASSI",   "Rim",     "0612000008", "Marrakech",  sdf.parse("12/02/1983")),
                new Femme("MRANI",   "Zineb",   "0612000009", "Tanger",     sdf.parse("08/08/1978")),
                new Femme("NACIRI",  "Loubna",  "0612000010", "Safi",       sdf.parse("14/05/1972"))
        };
        for (Femme f : femmes) femmeService.create(f);

        System.out.println("\n=== Création de 5 hommes ===");
        Homme[] hommes = {
                new Homme("SAFI",      "Said",    "0611000001", "Marrakech",  sdf.parse("01/01/1960")),
                new Homme("BENALI",    "Omar",    "0611000002", "Casablanca", sdf.parse("20/03/1965")),
                new Homme("CHERKAOUI", "Youssef", "0611000003", "Rabat",      sdf.parse("11/07/1970")),
                new Homme("LAHLOU",    "Khalid",  "0611000004", "Fès",        sdf.parse("05/05/1955")),
                new Homme("ZIANI",     "Hassan",  "0611000005", "Agadir",     sdf.parse("30/09/1962"))
        };
        for (Homme h : hommes) hommeService.create(h);

        System.out.println("\n=== Création des mariages ===");

        mariageService.create(new Mariage(
                sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0,
                hommes[0], femmes[3]   // KARIMA ALAMI
        ));
        mariageService.create(new Mariage(
                sdf.parse("03/09/1990"), null, 4,
                hommes[0], femmes[0]   // SALIMA RAMI
        ));
        mariageService.create(new Mariage(
                sdf.parse("03/09/1995"), null, 2,
                hommes[0], femmes[1]   // AMAL ALI
        ));
        mariageService.create(new Mariage(
                sdf.parse("04/11/2000"), null, 3,
                hommes[0], femmes[2]   // WAFA ALAOUI
        ));

        mariageService.create(new Mariage(
                sdf.parse("10/05/1992"), sdf.parse("10/05/1998"), 1,
                hommes[1], femmes[4]
        ));
        mariageService.create(new Mariage(
                sdf.parse("15/06/2000"), null, 2,
                hommes[1], femmes[5]
        ));

        mariageService.create(new Mariage(sdf.parse("01/01/1991"), null, 1, hommes[2], femmes[6]));
        mariageService.create(new Mariage(sdf.parse("01/06/1993"), null, 0, hommes[2], femmes[7]));
        mariageService.create(new Mariage(sdf.parse("01/03/1995"), null, 2, hommes[2], femmes[8]));
        mariageService.create(new Mariage(sdf.parse("01/09/1999"), null, 3, hommes[2], femmes[9]));

        mariageService.create(new Mariage(
                sdf.parse("10/10/1995"), sdf.parse("10/10/2000"), 1,
                hommes[3], femmes[9]
        ));

        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║    TEST 1 : Liste des femmes     ║");
        System.out.println("╚══════════════════════════════════╝");
        List<Femme> toutLesFemmes = femmeService.findAll();
        toutLesFemmes.forEach(f ->
                System.out.println("  • " + f.getNom() + " " + f.getPrenom()
                        + " | Née le : " + sdf.format(f.getDateNaissance()))
        );

        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║  TEST 2 : Femme la plus âgée    ║");
        System.out.println("╚══════════════════════════════════╝");
        Femme laPlusAgee = femmeService.getFemmeLaPlusAgee();
        if (laPlusAgee != null)
            System.out.println("  → " + laPlusAgee.getNom() + " " + laPlusAgee.getPrenom()
                    + " (née le " + sdf.format(laPlusAgee.getDateNaissance()) + ")");

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║  TEST 3 : Épouses de SAFI Said (1989-2026)  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(
                hommes[0], sdf.parse("01/01/1989"), sdf.parse("31/12/2026"));
        epouses.forEach(f ->
                System.out.println("  • " + f.getNom() + " " + f.getPrenom())
        );

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  TEST 4 : Enfants de RAMI Salima (1990-2026)   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        int nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(
                femmes[0], sdf.parse("01/01/1990"), sdf.parse("31/12/2026"));
        System.out.println("  → Nombre d'enfants : " + nbEnfants);

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║  TEST 5 : Femmes mariées 2 fois ou plus     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        List<Femme> femmesDeuxFois = femmeService.getFemmesMarieesDeuxFoisOuPlus();
        if (femmesDeuxFois.isEmpty()) {
            System.out.println("  (aucune)");
        } else {
            femmesDeuxFois.forEach(f ->
                    System.out.println("  • " + f.getNom() + " " + f.getPrenom()
                            + " (" + f.getMariages().size() + " mariages)")
            );
        }

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  TEST 6 : Hommes mariés à 4 femmes (1989-2026) ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        List<Homme> hommes4femmes = mariageService.getHommesMariesAQuatreFemmes(
                sdf.parse("01/01/1989"), sdf.parse("31/12/2026"));
        if (hommes4femmes.isEmpty()) {
            System.out.println("  (aucun)");
        } else {
            hommes4femmes.forEach(h ->
                    System.out.println("  • " + h.getNom() + " " + h.getPrenom())
            );
        }
        long count = mariageService.getNombreHommesMariesAQuatreFemmes(
                sdf.parse("01/01/1989"), sdf.parse("31/12/2026"));
        System.out.println("  → Nombre total : " + count);

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║  TEST 7 : Détails mariages de SAFI Said            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        System.out.println("Nom : " + hommes[0].getNom() + " " + hommes[0].getPrenom().toUpperCase());

        List<Mariage> details = hommeService.getMariagesDetailsParHomme(hommes[0]);

        System.out.println("\nMariages En Cours :");
        int i = 1;
        for (Mariage m : details) {
            if (m.getDateFin() == null) {
                System.out.printf(
                        "%d. Femme : %-18s Date Début : %s    Nbr Enfants : %d%n",
                        i++,
                        m.getFemme().getPrenom().toUpperCase() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut()),
                        m.getNbrEnfant()
                );
            }
        }

        System.out.println("\nMariages échoués :");
        i = 1;
        for (Mariage m : details) {
            if (m.getDateFin() != null) {
                System.out.printf(
                        "%d. Femme : %-18s Date Début : %s%n",
                        i,
                        m.getFemme().getPrenom().toUpperCase() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut())
                );
                System.out.printf(
                        "   Date Fin : %s    Nbr Enfants : %d%n",
                        sdf.format(m.getDateFin()),
                        m.getNbrEnfant()
                );
                i++;
            }
        }

        HibernateUtil.shutdown();
        System.out.println("\n=== Fin des tests ===");
    }
}
