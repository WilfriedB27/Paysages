/**
 * La classe Main est utilisée pour lancer le programme  
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Controllers.*;
import fr.iutfbleau.Models.*;
import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		Modele modele = new Modele();
		MenuJeu menu = new MenuJeu(modele);

		Runtime.getRuntime().addShutdownHook(new Ferme(modele));
		
	
	}
}
