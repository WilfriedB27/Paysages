/**
 * Controller du bouton pour jouer
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Controllers;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Models.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class JouerButtonListener implements ActionListener {
    private MenuJeu me;

    /**
     * Constructeur de la classe
     */
    
    public JouerButtonListener(MenuJeu me) {
        this.me = me;
    }

    /**
     * Fonction appelée lorsque l'on clique sur le bouton jouer du menu
     * Lance le jeu en fonction de la série choisie 
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        // Vérifier si une série a été sélectionnée
        String serieSelectionnee = me.getComboBoxSeries().getSelectedItem().toString();
        if (serieSelectionnee == null || serieSelectionnee.isEmpty()) {
            JOptionPane.showMessageDialog(me, 
                "Veuillez sélectionner une série avant de jouer.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode sans lancer le jeu
        }
        me.updateSerieSelectionnee(serieSelectionnee);
        System.out.println("Le bouton 'Jouer' a été cliqué avec la série : " + serieSelectionnee);
        me.lancerJeu(); 
    }
}
