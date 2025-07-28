/**
 * Controller du bouton poour séléctionner la série voulue
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
*/

package fr.iutfbleau.Controllers;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Models.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SerieSelectionListener implements ActionListener {
    private MenuJeu me;

    /**
     * Constructeur de la classe
     */

    public SerieSelectionListener(MenuJeu me) {
        this.me = me;  // Référence au menu
    }

    /**
     * Met à jour la série séléctionnée 
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String serieSelectionnee = (String) me.getComboBoxSeries().getSelectedItem();
        if (serieSelectionnee != null) {
            me.updateSerieSelectionnee(serieSelectionnee);
            System.out.println("Série sélectionnée dans le listener : " + serieSelectionnee);
        }
    }
}

