package org.example;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("stockdb");

        CategorieService catS = new CategorieService(emf);
        CommandeService comS = new CommandeService(emf);
        ProduitService ps = new ProduitService(emf);
        LigneCommandeService ls = new LigneCommandeService(emf);

        try {
            System.out.println("=== Création des catégories ===");
            Categorie cat1 = new Categorie("Processeurs", "CPU Intel et AMD");
            Categorie cat2 = new Categorie("Ecrans", "Moniteurs et dalles");
            catS.save(cat1);
            catS.save(cat2);

            System.out.println("\n=== Création des produits ===");
            Produit p1 = new Produit("Intel i9", 1200, cat1);
            Produit p2 = new Produit("AMD Ryzen 5", 350, cat1);
            Produit p3 = new Produit("Ecran 27\"", 240, cat2);
            Produit p4 = new Produit("Cable HDMI", 50, cat2);  // prix < 100

            ps.save(p1);
            ps.save(p2);
            ps.save(p3);
            ps.save(p4);

            System.out.println("\n=== Création des commandes ===");
            Commande c1 = new Commande(LocalDate.of(2023, 3, 14));
            Commande c2 = new Commande(LocalDate.of(2024, 6, 20));
            Commande c3 = new Commande(LocalDate.of(2025, 1, 10));
            comS.save(c1);
            comS.save(c2);
            comS.save(c3);

            System.out.println("\n=== Création des lignes de commande ===");
            LigneCommandeProduit l1 = new LigneCommandeProduit(c1, p1, 2);
            LigneCommandeProduit l2 = new LigneCommandeProduit(c1, p2, 1);
            LigneCommandeProduit l3 = new LigneCommandeProduit(c2, p3, 3);
            LigneCommandeProduit l4 = new LigneCommandeProduit(c3, p4, 5);
            ls.save(l1);
            ls.save(l2);
            ls.save(l3);
            ls.save(l4);

            System.out.println("\n=== TEST 1 : Liste de tous les produits ===");
            List<Produit> tousProduits = ps.findAll();
            for (Produit p : tousProduits) {
                System.out.println(p);
            }

            System.out.println("\n=== TEST 2 : Produits de la catégorie 'Processeurs' (id=" + cat1.getId() + ") ===");
            List<Produit> prodParCat = ps.findByCategorie(cat1.getId());
            for (Produit p : prodParCat) {
                System.out.println(p);
            }

            System.out.println("\n=== TEST 3 : Produits commandés entre 2023-01-01 et 2024-12-31 ===");
            List<Produit> prodEntresDates = ps.findProduitsCommandesEntreDates(
                    LocalDate.of(2023, 1, 1),
                    LocalDate.of(2024, 12, 31)
            );
            for (Produit p : prodEntresDates) {
                System.out.println(p);
            }

            System.out.println("\n=== TEST 4 : Produits de la commande id=" + c1.getId() + " ===");
            List<Produit> prodCommande = ps.findProduitsByCommande(c1.getId());
            for (Produit p : prodCommande) {
                System.out.println(p);
            }

            System.out.println("\n=== TEST 5 : Produits dont le prix > 100 DH (NamedQuery) ===");
            List<Produit> produitsChers = ps.findProduitsChers();
            for (Produit p : produitsChers) {
                System.out.println(p);
            }

            System.out.println("\n=== TEST 6 : Liste des commandes ===");
            List<Commande> commandes = comS.findAll();
            for (Commande c : commandes) {
                System.out.println(c);
            }

        } finally {
            emf.close();
        }
    }
}
