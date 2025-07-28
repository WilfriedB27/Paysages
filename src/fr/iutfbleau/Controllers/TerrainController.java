/**
 * Controller de la phase de jeu  
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
*/

package fr.iutfbleau.Controllers;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Models.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;


public class TerrainController implements MouseListener, KeyListener {
	
	private ArrayList<Tuile> tuiles; 
	private Terrain t;
	private Modele modele;

	/**
	 * Constructeur de la classe 
	 */

	public TerrainController(ArrayList<Tuile> tuiles, Terrain t, Modele m){
		this.tuiles = tuiles;
		this.t = t;
		this.modele = m;
	}
	
	/**
	 * Fonction appelée quand on clique avec le bouton gauche de la souris en jeu
	 */

	public void mouseClicked(MouseEvent e){
		/* Récupération des coordonnées de la souris */
		int posX = e.getX();
		int posY = e.getY();
		boolean aDessiner = false;

		Tuile tuilePlusProche = t.plusProche(posX, posY);
		/* Ajouter fonction qui vérifie si tuilePlusProche est bien à côté d'une tuile déjà visible */
		ArrayList<Tuile> mesAdjacents = t.getAdjacent(tuilePlusProche);

		for(Tuile tuile: mesAdjacents){
			if(tuile.getVisible() == true){
				aDessiner = true;
			}
		}

		if(aDessiner && tuilePlusProche.getVisible() == false){
			for(Tuile tuile: mesAdjacents){
				if(tuile.getVisible() == false){
					Color c = new Color(180, 180, 180, 100);
					Color[] colors = {c, c, c, c, c, c};
					tuile.setColor(colors);
				}
			}
			
			int serie = this.modele.getIdOfSerie(this.modele.getSerieSelectionnee());
			/*Color c1 = this.modele.getFirstColor(serie, this.t.getTuileAPose());
			Color c2 = this.modele.getSecondColor(serie, this.t.getTuileAPose());
			int p1 = this.modele.getFirstProportion(serie, this.t.getTuileAPose());
			int p2 = this.modele.getSecondProportion(serie, this.t.getTuileAPose());
			Color[] colors = this.t.getTabColor(c1, c2, p1, p2);*/
			Color[] colors = this.t.getProchTuile().getColors();
			tuilePlusProche.setColor(colors);
			tuilePlusProche.setVisible();
			t.changeTuileAPose();
			
			Color prochC1 = this.modele.getFirstColor(serie, this.t.getTuileAPose());
			Color prochC2 = this.modele.getSecondColor(serie, this.t.getTuileAPose());
			int prochP1 = this.modele.getFirstProportion(serie, this.t.getTuileAPose());
			int prochP2 = this.modele.getSecondProportion(serie, this.t.getTuileAPose());
			Color[] newColors = this.t.getTabColor(prochC1, prochC2, prochP1, prochP2);
			this.t.changeProchColor(newColors);





			t.repaint();
			this.t.checkPoche(tuilePlusProche);
		}
		t.requestFocusInWindow();
	}


	public void mouseEntered(MouseEvent e){
		//
	}

	public void mouseExited(MouseEvent e){
		//
	}

	public void mousePressed(MouseEvent e){
		//
	}
	
	public void mouseReleased(MouseEvent e){
		//
	}


	/**
	 * Fonction appelée quand on clique sur les flèches directionnelles en jeu
	 */

	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case 38:
				t.setOffsetY(t.getOffsetY()-42);
				break;
			case 40:
				t.setOffsetY(t.getOffsetY()+42);
				break;
			case 37:
				t.setOffsetX(t.getOffsetX()-74);
				break;
			case 39:
				t.setOffsetX(t.getOffsetX()+74);
				break;
			case 32:
				this.t.rotate();
				break;
		}
		t.repaint();	
		t.requestFocusInWindow();
	}



	public void keyReleased(KeyEvent e){
		//
	}
	public void keyTyped(KeyEvent e){
		//
	}
		
}






