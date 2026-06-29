package com.example;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {

        ProjetService projetService      = new ProjetService();
        EmployeService employeService     = new EmployeService();
        TacheService tacheService       = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        Employe e1 = new Employe("Alami",   "Karim",   "0661001001");
        Employe e2 = new Employe("Bennani", "Sara",    "0662002002");
        Employe e3 = new Employe("Chraibi", "Mohamed", "0663003003");

        employeService.create(e1);
        employeService.create(e2);
        employeService.create(e3);
        System.out.println("Employés insérés.");

        Projet p1 = new Projet("Gestion de stock",   sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
        Projet p2 = new Projet("Application mobile", sdf.parse("01/03/2013"), sdf.parse("31/12/2013"));

        p1.setChefProjet(e1);
        p2.setChefProjet(e1);

        projetService.create(p1);
        projetService.create(p2);
        System.out.println("Projets insérés.");

        Tache t1 = new Tache("Analyse",        sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0, p1);
        Tache t2 = new Tache("Conception",     sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0, p1);
        Tache t3 = new Tache("Développement",  sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 3500.0, p1);
        Tache t4 = new Tache("Tests",          sdf.parse("01/05/2013"), sdf.parse("15/05/2013"),  800.0, p1);
        Tache t5 = new Tache("Conception UI",  sdf.parse("01/03/2013"), sdf.parse("15/03/2013"), 1200.0, p2);

        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);
        tacheService.create(t4);
        tacheService.create(t5);
        System.out.println("Tâches insérées.");

        EmployeTache et1 = new EmployeTache(e2, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
        EmployeTache et2 = new EmployeTache(e2, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
        EmployeTache et3 = new EmployeTache(e3, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
        EmployeTache et4 = new EmployeTache(e3, t4, sdf.parse("05/05/2013"), sdf.parse("12/05/2013"));
        EmployeTache et5 = new EmployeTache(e2, t5, sdf.parse("05/03/2013"), sdf.parse("14/03/2013"));

        employeTacheService.create(et1);
        employeTacheService.create(et2);
        employeTacheService.create(et3);
        employeTacheService.create(et4);
        employeTacheService.create(et5);
        System.out.println("Affectations EmployeTache insérées.\n");


        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 1 – Tâches planifiées pour le projet id=" + p1.getId());
        System.out.println("════════════════════════════════════════════════════════");
        projetService.getTachesPlanifieesParProjet(p1.getId());

        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 2 – Tâches réalisées pour le projet id=" + p1.getId());
        System.out.println("════════════════════════════════════════════════════════");
        projetService.getTachesRealiseesPourProjet(p1.getId());

        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 3 – Tâches réalisées par l'employé id=" + e2.getId());
        System.out.println("════════════════════════════════════════════════════════");
        employeService.getTachesRealiseesByEmploye(e2.getId());

        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 4 – Projets gérés par l'employé id=" + e1.getId());
        System.out.println("════════════════════════════════════════════════════════");
        employeService.getProjetsGeresByEmploye(e1.getId());

        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 5 – Tâches dont le prix > 1000 DH (NamedQuery)");
        System.out.println("════════════════════════════════════════════════════════");
        tacheService.getTachesPrixSuperieur1000();

        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   TEST 6 – Tâches réalisées entre le 01/03/2013 et le 30/04/2013");
        System.out.println("════════════════════════════════════════════════════════");
        tacheService.getTachesEntreDeuxDates(
                sdf.parse("01/03/2013"),
                sdf.parse("30/04/2013")
        );

        HibernateUtil.shutdown();
        System.out.println("Terminé.");
    }
}
