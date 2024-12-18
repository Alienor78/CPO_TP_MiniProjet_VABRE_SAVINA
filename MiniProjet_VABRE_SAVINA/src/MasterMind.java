import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*
 * SAVINA Liza
 * VABRE Aliénor
 * 29/11/2024
 */

/**
 *
 * @author alien
 */
public class MasterMind extends JFrame {

    private Color[] couleurs = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private Map<Color, Integer> couleurToNumero = new HashMap<>();
    private JButton btnValider;

    private Integer[][] plateauDeJeu = new Integer[8][4]; 
    private JButton[][] grilleButtons = new JButton[8][4];
    private JButton[] combinaisonButtons = new JButton[4];
    private int[] combinaisonNumerique = new int[4]; 
     
    private int[] combinaisonSecrete = new int[4];
    
    private JButton[][] indicesButtons = new JButton[8][4];
    

    /**
     * Constructeur principal
     */
    public MasterMind() {
        setTitle("MasterMind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 600);

        initMapCouleurs(); // Associe les couleurs aux numéros
        initComponents();
        configurerPanneauGrille();
        configurerPanneauCombinaison();
        configurerPanneauIndices();

        // Listener pour le bouton Valider
        btnvalider.addActionListener(e -> validerCombinaison());
        btnRègles.addActionListener(e -> afficherRegles());
        lancerPartie();
    }
    
    private void afficherRegles() {
    String message = "Bienvenue dans MasterMind !\n\n"
            + "Règles du jeu :\n"
            + "1. Le but du jeu est de trouver la combinaison secrète composée de 4 couleurs.\n"
            + "2. À chaque tour, propose une combinaison en changeant les couleurs des boutons.\n"
            + "3. Après avoir validé, des indices apparaîtront :\n"
            + "   - Noir : une couleur est bien placée.\n"
            + "   - Blanc : une couleur est correcte mais mal placée.\n"
            + "4. Tu as 8 tentatives pour trouver la combinaison secrète.\n\n"
            + "Bonne chance !";

    JOptionPane.showMessageDialog(this, message, "Règles du jeu", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Initialise la correspondance entre les couleurs et les numéros.
     */
    private void initMapCouleurs() {
        for (int i = 0; i < couleurs.length; i++) {
            couleurToNumero.put(couleurs[i], i + 1); // Associe les couleurs à des numéros (1, 2, 3, 4, ...)
        }
    }

    /**
     * Configure les boutons dans le panneau de grille.
     */
    private void configurerPanneauGrille() {
        PanneauGrille.setLayout(new GridLayout(8, 4, 3, 3));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                grilleButtons[i][j] = new JButton();
                grilleButtons[i][j].setEnabled(false); // Désactivé par défaut
                grilleButtons[i][j].setBackground(Color.WHITE);
                PanneauGrille.add(grilleButtons[i][j]);
            }
        }
    }

    /**
     * Configure les boutons dans le panneau de combinaison.
     */
    private void configurerPanneauCombinaison() {
        Combinaison.setLayout(new GridLayout(1, 4, 3, 3));
        // Ajuster la taille des boutons pour correspondre au tableau principal
        

        for (int i = 0; i < 4; i++) {
            combinaisonButtons[i] = new JButton();
            combinaisonButtons[i].setBackground(couleurs[0]); // Couleur initiale
            combinaisonNumerique[i] = couleurToNumero.get(couleurs[0]); // Numéro initial
            final int index = i;
            combinaisonButtons[i].addActionListener(e -> {
                // Change la couleur et met à jour le numéro correspondant
                Color currentColor = combinaisonButtons[index].getBackground();
                int nextIndex = (indexCouleur(couleurs, currentColor) + 1) % couleurs.length;
                Color nextColor = couleurs[nextIndex];
                combinaisonButtons[index].setBackground(nextColor);
                combinaisonNumerique[index] = couleurToNumero.get(nextColor);
            });
            
            Combinaison.add(combinaisonButtons[i]);
        }
    }
    
    private void configurerPanneauIndices() {
        panneauIndices.setLayout(new GridLayout(8, 4, 3, 1));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                indicesButtons[i][j] = new JButton();
                indicesButtons[i][j].setEnabled(false);
                indicesButtons[i][j].setBackground(Color.GRAY); // Par défaut, gris
                panneauIndices.add(indicesButtons[i][j]);
            }
        }
    }
    
    /**
     * Retourne l'index d'une couleur donnée dans le tableau de couleurs.
     */
    private int indexCouleur(Color[] couleurs, Color couleur) {
        for (int i = 0; i < couleurs.length; i++) {
            if (couleurs[i].equals(couleur)) {
                return i;
            }
        }
        return -1; // Couleur non trouvée
    }
    
    private int couleurEnNumero(Color couleur) {
        return couleurToNumero.getOrDefault(couleur, -1); // Retourne -1 si la couleur n'existe pas
    }
    
    private void lancerPartie() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            combinaisonSecrete[i] = random.nextInt(couleurs.length) + 1;
        }
        System.out.println("Combinaison secrète : ");
        for (int n : combinaisonSecrete) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
    
    /**
     * Valide la combinaison en cours, l'ajoute au plateau et l'affiche dans la grille.
     */
   
    
    private int ligneActuelle = 0; 

    public void validerCombinaison() {
    // Récupérer la combinaison saisie en couleur.
        Color[] combinaisonSaisie = new Color[4];
        for (int i = 0; i < 4; i++) {
            combinaisonSaisie[i] = combinaisonButtons[i].getBackground(); 
        }

    // Afficher la combinaison dans la matrice de jeu
        for (int colonne = 0; colonne < 4; colonne++) {
            grilleButtons[ligneActuelle][colonne].setBackground(combinaisonSaisie[colonne]);
        }

        int[] combinaisonSaisieNumerique = new int[4];
        for (int i = 0; i < 4; i++) {
            combinaisonSaisieNumerique[i] = couleurEnNumero(combinaisonButtons[i].getBackground());
        }
        
    boolean[] secretUtilise = new boolean[4]; // Pour marquer les numéros déjà utilisés
    boolean[] saisieUtilisee = new boolean[4];

    // Vérification des pions bien placés (noirs)
        int pionsBienPlaces = 0;
        for (int i = 0; i < 4; i++) {
            if (combinaisonSaisieNumerique[i] == combinaisonSecrete[i]) {
                pionsBienPlaces++;
                secretUtilise[i] = true;
                saisieUtilisee[i] = true;
            }
        }

    // Vérification des pions mal placés (blancs)
    int pionsMalPlaces = 0;
    for (int i = 0; i < 4; i++) {
        if (!saisieUtilisee[i]) {
            for (int j = 0; j < 4; j++) {
                if (!secretUtilise[j] && combinaisonSaisieNumerique[i] == combinaisonSecrete[j]) {
                    pionsMalPlaces++;
                    secretUtilise[j] = true;
                    saisieUtilisee[i] = true;
                    break;
                }
            }
        }
    }


    // Étape 4 : Afficher les indices à gauche de la matrice
    int index = 0;

    // Affiche les pions noirs (bien placés)
        for (int i = 0; i < pionsBienPlaces; i++) {
            indicesButtons[ligneActuelle][index].setBackground(Color.BLACK);
            index++;
        }

    // Affiche les pions blancs (mal placés)
        for (int i = 0; i < pionsMalPlaces; i++) {
            indicesButtons[ligneActuelle][index].setBackground(Color.WHITE);
            index++;
        }
        
        if (pionsBienPlaces == 4) {
            JOptionPane.showMessageDialog(null, "Bravo ! Vous avez trouvé la combinaison !");
            reinitialiserPartie();
            return;
        }

        if (ligneActuelle == grilleButtons.length - 1) { // Dernière tentative échouée
            System.out.println("Défaite : dernière tentative atteinte.");
            JOptionPane.showMessageDialog(null, "Dommage ! Vous avez perdu. La combinaison secrète était : " + afficherCombinaisonSecrete());
            reinitialiserPartie();
            return;
        }
        
        System.out.println("Ligne actuelle : " + ligneActuelle);
        ligneActuelle++;
        
    }
        private String[] nomsCouleurs = {"RED", "BLUE", "GREEN", "YELLOW"};
        
        private String afficherCombinaisonSecrete() {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for (int i = 0; i < combinaisonSecrete.length; i++) {
                sb.append(nomsCouleurs[combinaisonSecrete[i] - 1]); // Récupère le nom de la couleur
                if (i < combinaisonSecrete.length - 1) {
                    sb.append(" | ");
                }
            }
            sb.append(" ]");
            return sb.toString();
        }



    private void reinitialiserPartie() {
        ligneActuelle = 0;
        plateauDeJeu = new Integer[8][4];
        lancerPartie();
        for (JButton[] ligne : grilleButtons) {
            for (JButton bouton : ligne) {
                bouton.setBackground(Color.WHITE);
            }
        }
        for (JButton[] ligneIndices : indicesButtons) {
            for (JButton bouton : ligneIndices) {
                bouton.setBackground(Color.GRAY);
            }
        }
    }
   
    
        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
       
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauGrille = new javax.swing.JPanel();
        Combinaison = new javax.swing.JPanel();
        btnvalider = new javax.swing.JButton();
        panneauIndices = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRègles = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanneauGrille.setBackground(new java.awt.Color(200, 200, 200));

        Combinaison.setBackground(new java.awt.Color(200, 200, 200));

        javax.swing.GroupLayout CombinaisonLayout = new javax.swing.GroupLayout(Combinaison);
        Combinaison.setLayout(CombinaisonLayout);
        CombinaisonLayout.setHorizontalGroup(
            CombinaisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        CombinaisonLayout.setVerticalGroup(
            CombinaisonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        btnvalider.setText("valider");
        btnvalider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvaliderActionPerformed(evt);
            }
        });

        panneauIndices.setBackground(new java.awt.Color(200, 200, 200));

        javax.swing.GroupLayout panneauIndicesLayout = new javax.swing.GroupLayout(panneauIndices);
        panneauIndices.setLayout(panneauIndicesLayout);
        panneauIndicesLayout.setHorizontalGroup(
            panneauIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panneauIndicesLayout.setVerticalGroup(
            panneauIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText("Panneau des combinaisons enregistrées : ");

        jLabel2.setText("Panneau des indices : ");

        jLabel3.setText("Proposez une combinaison :");

        jLabel4.setText("Jeu du master mind :");

        btnRègles.setText("Règles du jeu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panneauIndices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                            .addComponent(Combinaison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanneauGrille, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnvalider))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(315, 315, 315)
                        .addComponent(btnRègles)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRègles))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panneauIndices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanneauGrille, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Combinaison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnvalider)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnvaliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvaliderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnvaliderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MasterMind().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Combinaison;
    private javax.swing.JPanel PanneauGrille;
    private javax.swing.JButton btnRègles;
    private javax.swing.JButton btnvalider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panneauIndices;
    // End of variables declaration//GEN-END:variables

    

   


}
