/*
 * SAVINA Liza
 * VABRE Aliénor
 * 20/11/2024
 */
package miniprojet_vabre_savina;

import java.util.ArrayList;

/**
 *
 * @author alien
 * @author Liza Savina
 */
public class MiniProjet_VABRE_SAVINA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //tests de la classe Pion et combianaison:
        
        /* Pion[] pions1 = { new Pion('R'), new Pion('B'), new Pion('G'), new Pion('Y') };
        Pion[] pions2 = { new Pion('G'), new Pion('G'), new Pion('B'), new Pion('Y') };
        
        Combinaison combinaison1 = new Combinaison(pions1);
        Combinaison combinaison2 = new Combinaison(pions2);
        
        System.out.println("Combinaison 1 : " + combinaison1.toString());
        System.out.println("Combinaison 2 : " + combinaison2.toString());
        
        int[] resultat = combinaison1.comparer(combinaison2);
        System.out.println("Pions bien places : " + resultat[0]);
        System.out.println("Pions mal places : " + resultat[1]);*/
        
        /*ArrayList<Character> couleursDisponibles = new ArrayList<>();
        couleursDisponibles.add('R');
        couleursDisponibles.add('B');
        couleursDisponibles.add('G');
        couleursDisponibles.add('Y');
        
        Combinaison combinaisonAleatoire = Combinaison.genereAleatoire(4, couleursDisponibles);
        System.out.println("Combinaison aleatoire : " + combinaisonAleatoire.toString());*/
        
         
       /* Pion[] pionsSecretes = { new Pion('R'), new Pion('B'), new Pion('G'), new Pion('Y') };
        Combinaison combinaisonSecrete = new Combinaison(pionsSecretes);

      
        PlateauDeJeu plateau = new PlateauDeJeu(combinaisonSecrete, 10);
        
        Pion[] tentative1 = { new Pion('R'), new Pion('Y'), new Pion('G'), new Pion('B') };
        plateau.proposerCombinaison(new Combinaison(tentative1));

        Pion[] tentative2 = { new Pion('R'), new Pion('B'), new Pion('G'), new Pion('Y') };
        plateau.proposerCombinaison(new Combinaison(tentative2));
        
        plateau.afficherPlateau();
        
        if (plateau.estVictoire()) {
            System.out.println("Bravo ! Vous avez gagne !");
        } else if (plateau.estDefaite()) {
            System.out.println("Dommage, vous avez perdu...");
        }
    }
    */
   
        ArrayList<Character> couleursDisponibles = new ArrayList<>();
        couleursDisponibles.add('R');
        couleursDisponibles.add('B');
        couleursDisponibles.add('G');
        couleursDisponibles.add('Y');
        
        Partie partie1 = new Partie(4,8,couleursDisponibles);
        
        Pion[] pionsSecretes = partie1.plateau.combinaisonSecrete.elements; // On récupère la combinaison secrète
        
        /*Combinaison tentativeGagnante = new Combinaison(pionsSecretes);   // Crée une combinaison identique pour gagner
        partie1.plateau.proposerCombinaison(tentativeGagnante);

        if (partie1.plateau.estVictoire()) {
            System.out.println("Bravo ! Vous avez trouve la combinaison secrete !");
        } else {
            System.out.println("Échec : la combinaison n'est pas correcte.");
        } */
        
        partie1.LancerPartie();
        
        
}
}
