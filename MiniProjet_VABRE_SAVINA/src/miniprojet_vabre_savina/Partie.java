/*
 * SAVINA Liza
 * VABRE Aliénor
 * 29/11/2024
 */
package miniprojet_vabre_savina;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alien
 */
public class Partie {
    
    
    PlateauDeJeu plateau;
    ArrayList<Character> couleursDisponibles;
    int nbToursMax;
    int tailleCombinaison;
    
    
    
    public Partie(){
        Scanner sc = new Scanner(System.in);

        // Étape 1 : Choisir le nombre de couleurs disponibles
        System.out.println("Combien de couleurs souhaitez-vous utiliser ? (min : 2, max : 10)");
        int nbCouleurs;
        while (true) {
            nbCouleurs = sc.nextInt();
            if (nbCouleurs >= 2 && nbCouleurs <= 10) break;
            System.out.println("Veuillez entrer un nombre entre 2 et 10.");
        }

        // Générer les couleurs disponibles automatiquement
        this.couleursDisponibles = new ArrayList<>();
        for (int i = 0; i < nbCouleurs; i++) {
            this.couleursDisponibles.add((char) ('A' + i)); // Couleurs : A, B, C, ...
        }

        // Étape 2 : Choisir la taille de la combinaison
        System.out.println("Quelle taille de combinaison souhaitez-vous ? (min : 1, max : 10)");
        while (true) {
            tailleCombinaison = sc.nextInt();
            if (tailleCombinaison >= 1 && tailleCombinaison <= 10) break;
            System.out.println("Veuillez entrer une taille entre 1 et 10.");
        }

        // Étape 3 : Choisir le nombre maximum de tours
        System.out.println("Combien de tours maximum souhaitez-vous ? (min : 1, max : 20)");
        while (true) {
            nbToursMax = sc.nextInt();
            if (nbToursMax >= 1 && nbToursMax <= 20) break;
            System.out.println("Veuillez entrer un nombre entre 1 et 20.");
        }

        // Générer la combinaison secrète
        Combinaison combinaisonSecrete = Combinaison.genereAleatoire(tailleCombinaison, couleursDisponibles);

        // Initialiser le plateau
        this.plateau = new PlateauDeJeu(combinaisonSecrete, nbToursMax);
    }
    
     public void afficherRegles() {
        System.out.println("Bienvenue dans le jeu du Mastermind !");
        System.out.println("Paramètres de cette partie :");
        System.out.println("- Taille de la combinaison : " + tailleCombinaison);
        System.out.println("- Nombre de couleurs disponibles : " + couleursDisponibles.size());
        System.out.println("- Couleurs disponibles : " + couleursDisponibles);
        System.out.println("- Nombre de tours maximum : " + nbToursMax);
        System.out.println("\nRègles :");
        System.out.println("- Devinez la combinaison secrète.");
        System.out.println("- Un pion noir : bien placé.");
        System.out.println("- Un pion blanc : mal placé, mais présent.");
    }
    
     private Combinaison creerCombinaisonDepuisEntree(String entree) {
    Pion[] pions = new Pion[entree.length()];

    for (int i = 0; i < entree.length(); i++) {
        char couleur = entree.charAt(i);

        if (!couleursDisponibles.contains(couleur)) {
            throw new IllegalArgumentException("Couleur invalide : " + couleur + ". Couleurs valides : " + couleursDisponibles);
        }

        pions[i] = new Pion(couleur);
    }

    return new Combinaison(pions);
}
     
     
     
     
     public void LancerPartie(){
         Scanner sc = new Scanner(System.in);
         int tour = 1;
        
        
        while (!plateau.estVictoire() && !plateau.estDefaite()) {
        // Afficher le plateau
        plateau.afficherPlateau();
        
        // vérifier que le nombre de tentatives ne dépassent pas le nombre maximal.
        if (tour == nbToursMax){
            plateau.estDefaite();
            System.out.println("Dommage, vous avez perdu... La combinaison secrète était : " + plateau.combinaisonSecrete);
            break;
        }

        // Demander une tentative valide
        Combinaison tentative = null;
        while (tentative == null) {
            System.out.println("Tour " + tour + " : Proposez une combinaison (par exemple: 'ABCD...') dans les couleurs disponibles suivantes :" + couleursDisponibles);
            String entree = sc.nextLine().toUpperCase();

            if (entree.length() != plateau.combinaisonSecrete.taille) {
                System.out.println("Erreur : La combinaison doit comporter " + plateau.combinaisonSecrete.taille + " couleurs.");
                continue;
            }

            try {
                tentative = creerCombinaisonDepuisEntree(entree);
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
        
        // Proposer la tentative au plateau
        plateau.proposerCombinaison(tentative);

        // Vérifier si la partie est terminée
        if (plateau.estVictoire()) {
            System.out.println("Bravo ! Vous avez trouve la combinaison secrete !");
            System.out.println("Vous avez reussi en " + tour + " sur " + nbToursMax + " tours maximum.");
            break;
        } else if (plateau.estDefaite()) {
            System.out.println("Dommage, vous avez perdu... La combinaison secrete était : " + plateau.combinaisonSecrete);
            break;
        }

        tour++;
    }
}
             
}
     
