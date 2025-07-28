/**
 * Classe représentant une tuile  
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Vues;
import fr.iutfbleau.Controllers.*;
import fr.iutfbleau.Models.*;

import java.awt.*;
 
 public class Tuile {

    /**
     *  position de la tuile 
     */

     private int x, y; 

    /**
     * Couleur de la tuile
     */

     private Color[] color;

    /**
     * Constante du rayon de la tuile
     */

     private static final int RADIUS = 50;

    /**
     * Si la tuile est visible ou non 
     */

     private boolean estVisible = false;

     /**
      * Numero de la tuile (unique)
      */

     private int numeroTuile;

     private int[] mesPochesId;

 
    /** 
     * constructeur de la classe
     * @param x position x de la tuille
     * @param y position y de la tuile
     * @param color tableau de couleur de la tuile
     */

     public Tuile(int x, int y, Color[] color) {
         this.x = x;
         this.y = y;
         this.color = color;
	 this.mesPochesId = new int[2];
	 this.mesPochesId[0] = 0;
	 this.mesPochesId[1] = 0;
     }

    /**
     * Dessine la tuile
     * @param g paramètre graphique
     * @param pX position x de la tuile
     * @param pY position y de la tuile
     */

    public void dessiner(Graphics g, int pX, int pY) {
        Polygon hexagone = creerHexagone(pX, pY);
	    Color[] couleurs = { this.color[0], this.color[1], this.color[2], this.color[3], this.color[4], this.color[5]};
        g.setColor(couleurs[1]);
        g.fillPolygon(hexagone); 
	    if(this.getVisible()){
         	g.setColor(Color.BLACK);
         	g.drawPolygon(hexagone);
	    }
		//couleurs = new Color[] { this.color[0], this.color[1], color[2], this.color[3], this.color[4], this.color[5] };
	

	 	int[] xPoints = hexagone.xpoints;
	 	int[] yPoints = hexagone.ypoints;

	 	int centerX = pX;
	 	int centerY = pY;

	 	for(int i = 0; i < 6; i++){
	 		Polygon triangle = new Polygon();
	 		triangle.addPoint(centerX, centerY);
	 		triangle.addPoint(xPoints[i], yPoints[i]);
	 		triangle.addPoint(xPoints[(i+1)%6], yPoints[(i+1)%6]);

			g.setColor(couleurs[i]);
	 		g.fillPolygon(triangle);
		}
	 //}
     }
     
    /**
     * Créer un hexagone
     * @param x position x
     * @param y position y
     * @return l'hexagone
     */

     private Polygon creerHexagone(int x, int y) {
         Polygon hex = new Polygon();
         for (int i = 0; i < 6; i++) {
             double angle = Math.toRadians(60 * i);
             int x_i = (int) (x + RADIUS * Math.cos(angle));
             int y_i = (int) (y + RADIUS * Math.sin(angle));
             hex.addPoint(x_i, y_i);
         }
         return hex;
     }
 
    /**
     * Obtenir la position x de la tuile
     * @return la position x de la tuile
     */

     public int getX() {
         return this.x;
     }

    /**
     * Obtenir la position y de la tuile
     * @return la position y de la tuile
     */

     public int getY() {
         return this.y;
     }
    

    /**
     * Calcule distance
     * @param px position x
     * @param py postion y
     * @return la distance (double)
     */

     public double distanceAuPoint(int px, int py){
         return Math.sqrt(Math.pow(this.x - px, 2) + Math.pow(this.y - py, 2));
     }
     
    /**
     * Set la couleur 
     * @param c tableau de couleur
     */

     public void setColor(Color[] c){
         this.color = c;
     }
 
    /**
     * Rend visible la tuile
     */

     public void setVisible(){
         this.estVisible = true;
     }
 
    /**
     * Savoir si une tuile est visible ou non
     * @return false ou true  
     */

     public boolean getVisible(){
         return this.estVisible;
     }
 
    /**
     * Savoir le numero d'une tuile
     * @return numero de la tuile
     */

     public int getNumero(){
         return this.numeroTuile;
     }
 
    /**
     * Met à jour le numero d'une tuile
     * @param n numero de la tuile
     */

      public void setNumero(int n){
         this.numeroTuile = n;
     }

    /**
     * Obtenir les couleurs qui composent une tuile
     * @return les couleurs qui composent une tuile
     */

	public Color[] getColors(){
		return this.color;
	}

	public int[] getPocheId(){
		return this.mesPochesId;
	}

	public void setPoche(int id){
		for(int i = 0; i < 2; i++){
			if(this.mesPochesId[i] == 0){
				this.mesPochesId[i] = id;
			}
		
		}
	
	}




 
 }
 
