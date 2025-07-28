/**
 * La classe Fin est utilisée pour lancer la fenêtre de fin
 * @author BRIGITTE Wilfried
 * @author BRIDJA Abed
 * @author DUBREUIL Christopher
 */

 package fr.iutfbleau.Vues;

 import fr.iutfbleau.Models.*;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Arrays;
 
 public class Fin extends JFrame {
     private int scoreJoueur;
     private int[] scoreBD;
     private Modele modele;
     private String serie;
 
     /**
      * Constructeur de la classe
      * @param scoreJoueur score du joueur
      * @param scoreBD score des autres joueurs stocké dans la base de données
      * @param modele modèle du projet
      * @param serie nom de la série choisi
      */
     public Fin(int scoreJoueur, int[] scoreBD, Modele modele, String serie) {
         this.scoreJoueur = scoreJoueur;
         this.scoreBD = scoreBD;
         this.modele = modele;
         this.serie = serie;
 
         // Trie des scores dans l'ordre décroissant
         Arrays.sort(scoreBD);
 
         // Paramètres de la fenêtre
         setTitle("Fin de partie");
         setSize(1400, 700);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setLocationRelativeTo(null);
 
         // Calcul du rang du joueur
         int rank = calculateRank();
 
         // Chargement de l'image de fond
         ImageIcon backgroundImage = new ImageIcon("./res/Fin_Game.jpg");
 
         // Création d'un JPanel personnalisé pour dessiner l'image en arrière-plan
         JPanel backgroundPanel = new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
             }
         };
         backgroundPanel.setLayout(new BorderLayout()); // Utilise BorderLayout pour un placement flexible
 
         // Panel central pour les informations de score
         JPanel infoPanel = new JPanel(new GridLayout(5, 1));
         infoPanel.setOpaque(false);
 
         // Création des composants d'affichage de score
         JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
         messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
         messageLabel.setForeground(Color.YELLOW);
 
         JLabel scoreLabel = new JLabel("Votre score : " + scoreJoueur, SwingConstants.CENTER);
         scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
         scoreLabel.setForeground(Color.WHITE);
 
         JLabel rankLabel = new JLabel("Vous êtes le " + rank + "ème meilleur score pour cette série.", SwingConstants.CENTER);
         rankLabel.setFont(new Font("Arial", Font.PLAIN, 20));
         rankLabel.setForeground(Color.WHITE);
 
         JLabel scoresBD = new JLabel("Scores des autres joueurs : ", SwingConstants.CENTER);
         scoresBD.setFont(new Font("Arial", Font.PLAIN, 20));
         scoresBD.setForeground(Color.WHITE);
 
         DefaultListModel<Integer> listModel = new DefaultListModel<>();
         for (int i = scoreBD.length - 1; i >= 0; i--) {
             listModel.addElement(scoreBD[i]);
         }
 
         JList<Integer> scoreList = new JList<>(listModel);
         scoreList.setVisibleRowCount(5);
         scoreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         scoreList.setEnabled(false);
         scoreList.setFont(new Font("Arial", Font.PLAIN, 20));
         scoreList.setCellRenderer(new DefaultListCellRenderer() {
             @Override
             public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                 JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 label.setHorizontalAlignment(SwingConstants.CENTER);
                 label.setForeground(Color.WHITE);
                 return label;
             }
         });
 
         JScrollPane scrollPane = new JScrollPane(scoreList);
         scrollPane.setOpaque(false);
         scrollPane.getViewport().setOpaque(false);
 
         // Ajout des composants au panneau central
         infoPanel.add(messageLabel);
         infoPanel.add(scoreLabel);
         infoPanel.add(rankLabel);
         infoPanel.add(scoresBD);
         infoPanel.add(scrollPane);
 
         // Ajout du panneau central au centre du panneau de fond
         backgroundPanel.add(infoPanel, BorderLayout.CENTER);
 
         // Panel pour les boutons
         JPanel buttonPanel = new JPanel();
         buttonPanel.setOpaque(false);
         buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20)); // Place les boutons en bas à droite avec un espacement
 
         // Bouton "Rejouer"
         JButton replayButton = new JButton("Rejouer");
         replayButton.setFont(new Font("Arial", Font.BOLD, 20));
         replayButton.setBackground(Color.BLACK);
         replayButton.setForeground(Color.ORANGE);
         replayButton.setPreferredSize(new Dimension(140, 50));
         replayButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Code pour redémarrer le jeu
                 dispose();
                 new MenuJeu(modele);  // Ferme la fenêtre actuelle
                 // Ajouter le code pour relancer le jeu ici
             }
         });
 
         // Bouton "Quitter"
         JButton quitButton = new JButton("Quitter");
         quitButton.setFont(new Font("Arial", Font.BOLD, 20));
         quitButton.setBackground(Color.BLACK);
         quitButton.setForeground(Color.ORANGE);
         quitButton.setPreferredSize(new Dimension(120, 50));
         quitButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Code pour quitter le jeu
                 System.exit(0);
             }
         });
 
         // Ajout des boutons au panneau de boutons
         buttonPanel.add(replayButton);
         buttonPanel.add(quitButton);
 
         // Ajout du panneau de boutons au bas du panneau de fond
         backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
 
         // Ajout du panel de fond à la fenêtre
         setContentPane(backgroundPanel);
         setVisible(true);
 
         // Ajout du score à la base de données
         modele.ajouterScore(scoreJoueur, serie);
     }
 
     /**
      * Calcul du rang du joueur en fonction de son score
      * @return un entier représentant le rang du joueur en fonction de son score
      */
     private int calculateRank() {
         int rang = 1;
 
         // Vérifier si le tableau scoreBD est vide
         if (scoreBD.length == 0) {
             return rang;
         }
 
         // Comparaison avec les scores de la base de données
         for (int score : scoreBD) {
             if (score > scoreJoueur) {
                 rang++;
             }
         }
         return rang;
     }
 }
 