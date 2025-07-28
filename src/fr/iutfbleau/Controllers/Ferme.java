/**
 * La classe Ferme est utilisé pour arrêter la connection avec la base de données
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Controllers;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Models.*;


public class Ferme extends Thread {

    private final Modele mo;

    public Ferme(Modele mo) {
        this.mo = mo;
    }


    /** 
     * Fonction pour appeler la méthode pour fermer la connectrion
     */

    @Override
    public void run() {
        this.mo.fermerConnection();
        System.out.println("Connexion fermée !");
    }
}

