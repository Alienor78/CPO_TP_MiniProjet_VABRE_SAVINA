package miniprojet_vabre_savina;


import java.util.ArrayList;
import java.util.Random;

/*
 * SAVINA Liza
 * VABRE Aliénor
 * 20/11/2024
 */

/**
 *
 * @author Liza Savina
 * @author alien
 */
public class Combinaison {
    
    Pion [] elements;
    int taille;
    
     public Combinaison(Pion[] elements) {
        this.elements = elements;
        this.taille = elements.length;
    }
        
    public static Combinaison genereAleatoire(int taille, ArrayList<Character>couleursDisponibles){
        if (couleursDisponibles.isEmpty()) {
        throw new IllegalArgumentException("La liste des couleurs disponibles ne peut pas être vide !");
    }

    Random random = new Random();
    Pion[] elements = new Pion[taille];

    for (int i = 0; i < taille; i++) {
        char couleur = couleursDisponibles.get(random.nextInt(couleursDisponibles.size()));
        elements[i] = new Pion(couleur);
    }
    return new Combinaison(elements);
    }
    
    public int[] comparer(Combinaison autre){
        int blanc = 0; //Bien placés
        int noir = 0; //Mal placés
        
        boolean [] VisitedAutre = new boolean [autre.taille]; // On marque les pions déjà utilisés dans l'autre combinaison.
        boolean [] VisitedCurrent = new boolean [this.taille]; //On marque les pions déjà comptés dans la combinaison actuelle.
        
        // ces deux tableaux permettent d'éviter des erreurs comme des doublons.
        
        for (int i = 0; i < taille; i++) {
            if (this.elements[i].getCouleur() == autre.elements[i].getCouleur()) { //Si la couleur du pion est identique dans les deux combinaison pour une même place alors:
            noir++; //On ajoute un pion noir.
            VisitedAutre[i] = true; //On note son indice comme visité dans la liste.
            VisitedCurrent[i] = true; //On note son indice comme visité dans la liste.
    }
}
        for (int i = 0; i < taille; i++) {
            if (!VisitedCurrent[i]){ //On parcours tous les pions qui n'ont pas été comptés comme noirs avec la précédente boucle.
                for (int j = 0; j < taille; j++){ 
                    if(!VisitedAutre[j]&&this.elements[i].getCouleur() == autre.elements[j].getCouleur()){ //Pour chaque pions restant, on cherche dans la combinaison "Autre", une couleur qui n'a pas encore été utilisée.
                        blanc++; //Si c'est vrai, on a un pion blanc en plus.
                        VisitedAutre[j]=true; // On note son indice comme visité dans la liste.
                        break;
                    }
                }
            }
} return new int[] {noir,blanc};
    }
    
     @Override
    public String toString() {
    String result = "";
    for (Pion pion : elements) {
        result += pion.getCouleur();
    }
    return result;
}
}
