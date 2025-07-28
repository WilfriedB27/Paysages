/**
 * Classe utilisé pour gérer le terrain et les tuiles 
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Vues;
import fr.iutfbleau.Controllers.*;
import fr.iutfbleau.Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
 
 public class Terrain extends JPanel {
     private ArrayList<Tuile> tuiles;
     private ArrayList<Poche> poches;
     private int countPoche = 1;
     private int largeurEcran;
     private int hauteurEcran;
     private int tuileParLigne;
     private int offsetX = 0;
     private int offsetY = 0;
     private Modele modele;
     private int tuileAPose = 1;
     private double score = 1.0;
     private Tuile prochaineTuile;
     private int tuilesRestantes = 49;
     private JFrame fenetre;
     private MenuJeu menuJeu;
    

    /**
     * Contructeur de la classe
     * @param m modele du projet
     * @param fenetre fenetre de jeu
     */

     public Terrain(Modele m, JFrame fenetre, MenuJeu menuJeu){
         this.fenetre = fenetre;
	     this.modele = m;
         this.tuiles = new ArrayList<>();
         this.poches = new ArrayList<>();
         this.menuJeu = menuJeu;

         int X = 0;
         int Y = 0;
         int row = 0;
         int nbTuile = 1;
         Tuile firstTuile = null;
 	     int serie = this.modele.getIdOfSerie(this.modele.getSerieSelectionnee());


         for(int i = 1; i < 51; i++){
             for(int j = 1; j < 51; j++){
                 Color co = new Color(0, 180, 0, 0);
                 if(j%2==1){
                     if(i == 25 && j == 25){
			 /* Plûtot que de récupérer le nom de l'élément, récupére directement la
			  * premier couleur de la tuile Position 1 de la serie (variable juste au dessus) */
			 Color c1 = this.modele.getFirstColor(serie, 1);
			 /* Pareil pour la deuxième couleur */
			 Color c2 = this.modele.getSecondColor(serie, 1);
			 /* Récupérer aussi la proportion : 1 = couleur unique, 2 = 2/4 et 3 = 3/3 
			  * Méthode qui récupère les 2 couleurs + la proportion et qui donne une liste de Color[] qui correspond */
			 int p1 = this.modele.getFirstProportion(serie, 1);
			 int p2 = this.modele.getSecondProportion(serie, 1);
			 Color[] colors = this.getTabColor(c1, c2, p1, p2);
			 /* Donner cette liste de Color[] (6 couleurs) à la tuile */
			 Tuile t = new Tuile(X, Y, colors);
			 t.setNumero(nbTuile);
			 t.setVisible();
			 this.tuiles.add(t);
			 firstTuile = t;
			 this.tuileAPose++;	
                     } else {
			 Color[] colors = { co, co, co, co, co, co };
                         Tuile t = new Tuile(X, Y, colors);
                         t.setNumero(nbTuile);
                         this.tuiles.add(t);
                     }
                 } else {
                     Y = Y + 42;
		             Color[] colors = { co, co, co, co, co, co};
                     Tuile t = new Tuile(X, Y, colors);
                     t.setNumero(nbTuile);
                     this.tuiles.add(t);
                     Y = Y - 42;
                 }
                 X = X + 74;
                 nbTuile++;
             }
             row++;
             X = 0;
             Y = 88 * row;
         }
         this.tuileParLigne = 50;
         for(Tuile tuile: tuiles){
             if(tuile.getVisible() == true){
                 ArrayList<Tuile> mesAdjacents = this.getAdjacent(tuile);
                     
                 for(Tuile tu: mesAdjacents){
                     Color c = new Color(180, 180, 180, 100);
		             Color[] colors = {c, c, c, c, c, c };
                     tu.setColor(colors);
                 }
             }
         }
	
     
         offsetX = firstTuile.getX() - getWidth() / 2 - 700;
         offsetY = firstTuile.getY() - getHeight() / 2 - 400;
         repaint();


	     requestFocusInWindow(); 
         this.setFocusable(true);
         this.addKeyListener(new TerrainController(this.tuiles, this, this.modele));
         this.addMouseListener(new TerrainController(this.tuiles, this, this.modele));

	     Color prochC1 = this.modele.getFirstColor(serie, this.tuileAPose);
	     Color prochC2 = this.modele.getSecondColor(serie, this.tuileAPose);
	     int prochP1 = this.modele.getFirstProportion(serie, this.tuileAPose);
	     int prochP2 = this.modele.getSecondProportion(serie, this.tuileAPose);
	     Color[] prochColors = this.getTabColor(prochC1, prochC2, prochP1, prochP2);

	     this.prochaineTuile = new Tuile(0, 0, prochColors);
     }
 
     @Override
     protected void paintComponent(Graphics g){
         super.paintComponent(g);
         for(Tuile tuile : tuiles){
             int xAffiche = tuile.getX() - offsetX;
             int yAffiche = tuile.getY() - offsetY;
 
 
             if(xAffiche > -100 && xAffiche < getWidth() + 100 && yAffiche > -100 && yAffiche < getHeight() + 100){
                 tuile.dessiner(g, xAffiche, yAffiche);
             }
         }
	

	 g.setColor(Color.BLACK);
	 g.setFont(new Font("Arial", Font.BOLD, 20));
	 String texteScore = "Score : " + score;
	 int textWidth = g.getFontMetrics().stringWidth(texteScore);
	 g.setColor(Color.WHITE);
	 g.drawString(texteScore, getWidth() - textWidth - 10, 30);

     String texteTuilesRestantes = "Tuile restantes : "+ tuilesRestantes;
     int texteTuilesRestantesWidth = g.getFontMetrics().stringWidth(texteTuilesRestantes);
     g.drawString(texteTuilesRestantes, getWidth() - texteTuilesRestantesWidth - 10, 200);


	 int tuileX = getWidth() - 60;
	 int tuileY = 100;
	 this.prochaineTuile.dessiner(g, tuileX, tuileY);

     }
 

     /**
      * Renvoie la tuile la plus proche d'une certaines position
      * @param positionX position x de la souris
      * @param positionY position y de la souris
      * @return la tuile la plus proche
      */

     public Tuile plusProche(int positionX, int positionY){
         Tuile tuilePlusProche = null;
         double distanceMin = Double.MAX_VALUE;
 
         int adjustedX = positionX + this.offsetX;
         int adjustedY = positionY + this.offsetY;
 
         
 
         for(Tuile tuile: tuiles){
             double distance = tuile.distanceAuPoint(adjustedX, adjustedY);
             if(distance < distanceMin){
                 distanceMin = distance;
                 tuilePlusProche = tuile;
             }
         }
         return tuilePlusProche;
     }
     
     /**
      * Renvoie une liste de tuile adjacente à une tuile spécifique
      * @param tu tuile dont on cherche les tuile adjacentes
      * @return Liste de tuile adjacente à une tuile spécifique
      */

     public ArrayList<Tuile> getAdjacent(Tuile tu){
         ArrayList<Tuile> tuileAdj = new ArrayList<>();
         
         int maTuile = tu.getNumero();
         
         
 
         /* Je boucle sur toute mes tuiles */
         for(Tuile tuile: tuiles){
             /* Je récupère le numéro de ces tuiles */
             int numTuile = tuile.getNumero();
             
 
             /* Si tX, tY font partie des 6 cas possibles, j'ajoute la tuile au tableau tuileAdj */
 
             /* Premier cas, au dessus : numero - tuileParLigne */
             if(numTuile == (maTuile - this.tuileParLigne)){
                 tuileAdj.add(tuile);
             }
             /* En dessous */
             if(numTuile == (maTuile + this.tuileParLigne)){
                 tuileAdj.add(tuile);
             }
             /* Bas gauche */
             if(numTuile == maTuile-1){
                 if(numTuile%this.tuileParLigne != 0){
                     tuileAdj.add(tuile);
                 }
             }
             /* Bas droite */
             if(numTuile == maTuile+1 && ((maTuile%this.tuileParLigne) != 0)){
                 tuileAdj.add(tuile);
             }
 
             if(maTuile%2 == 1){
                 /* Haut gauche */
                 if(numTuile == ((maTuile-1) - this.tuileParLigne)){
                     if(numTuile%this.tuileParLigne != 0){
                         tuileAdj.add(tuile);
                     }
                 }
                 /* Haut droite */
                 if(numTuile == ((maTuile+1) - this.tuileParLigne)){
                     if(maTuile%this.tuileParLigne != 0){
                         tuileAdj.add(tuile);
                     }
                 }
             } else {
                 if(numTuile == ((maTuile-1) + this.tuileParLigne)){
                     if(numTuile%this.tuileParLigne != 0){
                         tuileAdj.add(tuile);
                     }
                 }
                 if(numTuile == ((maTuile+1) + this.tuileParLigne)){
                     if(maTuile%this.tuileParLigne != 0){
                         tuileAdj.add(tuile);
                     }
                 }
             }
             
         }
 
         return tuileAdj;
 
     }
 





    /**
     * Obtenir une tuile en fonction de son numero
     * @param n numero de la tuile
     * @return une tuile
     */

    private Tuile getTuile(int n){
        for(Tuile tuile: tuiles){
            if(tuile.getNumero() == n){
                 return tuile;
            }
        }
        return null;
    }
 
    /**
     * met à jour l'offsetX
     * @param n numero de la tuile
     */

    public void setOffsetX(int n){
        this.offsetX = n;
    }

    /**
     * met à jour l'offsetY
     * @param n numero de la tuile
     */

    public void setOffsetY(int n){
        this.offsetY = n;
    }

    /**
     * obtenir l'offset X
     * @param n numero de la tuile
     * @return l'offset X
     */

    public int getOffsetX(){
        return this.offsetX;
    }

    /**
     * obtenir l'offset Y
     * @param n numero de la tuile
     * @return l'offset Y
     */

    public int getOffsetY(){
        return this.offsetY;
    }

    /**
     * Obtenir le numero de la tuile à poser
     * @return le numero de la tuile à poser
     */

    public int getTuileAPose(){
        return this.tuileAPose;     
    }

    /**
     * Met à jour le nombre de tuile à poser et vérifie si en reste
     */

    public void changeTuileAPose(){
    	this.tuileAPose++;
        this.tuilesRestantes--;
        

        if(tuilesRestantes <= 0){
            //fenetre.dispose();
            int score = (int) this.score;
            menuJeu.showFin(score, modele.getScoresForSerie(modele.getSerieSelectionnee()), modele, modele.getSerieSelectionnee());

        }
        repaint();
    }

    /**
     * Obtenir le tableau de 6 couleurs pour former un hexagone (tuile) en fonction de 2 couleurs et de ses proportions, si le paramètre p1 vaut 2, alors on aura 2*c1 dans le tableau et pareil pour p2 et c2
     * @param c1 la première couleur de l'hexagone
     * @param c2 la deuxième couleur de l'hexagone
     * @param p1 la proportion de la première couleur de l'hexagone
     * @param p2 la proportion de la deuxième couleur de l'hexagone
     * @return un tableau de couleur 
     */

    public Color[] getTabColor(Color c1, Color c2, int p1, int p2){
     	Color[] mesCouleurs = new Color[6];
    	int colorPose = 0;
    	int i = 0;
    	while(colorPose < p1){
    		mesCouleurs[i] = c1;
    		i++;
    		colorPose++;
    	}
    	colorPose = 0;
    	while(colorPose < p2){
    		mesCouleurs[i] = c2;
    		i++;
    		colorPose++;
    	}
    	return mesCouleurs;
     }


     /**
      * Change la prochaine couleur
      * @param colors tableau de couleurs
      */

    public void changeProchColor(Color[] colors){
	    this.prochaineTuile.setColor(colors);
    }

     /**
      * Obtenir la prochaine tuile qui va être posée
      * @return la prochaine tuile
      */

    public Tuile getProchTuile(){
     	return this.prochaineTuile;
    }


    /**
     * Permet de faire une rotation sur la prochaine tuile à poser
     */

    public void rotate(){
        Color[] colors = this.prochaineTuile.getColors();
	    Color lastColor = colors[5];
	    for(int i = 5; i > 0; i--){
	       colors[i] = colors[i-1];
	    }
	    colors[0] = lastColor;
     	this.prochaineTuile.setColor(colors);
	    this.repaint();
    }



    /**
     * Verifie les poches du terrain
     * @param tuilePose la nouvelle tuille qui vient d'être posée
     */

    public void checkPoche(Tuile tuilePose){
		ArrayList<Tuile> adjacent = getAdjacent(tuilePose);
		Color[] maTuileColors = tuilePose.getColors();
		/* Je récupère les tuile adj de tuilePose */
		Tuile tuileBD = null;
		Tuile tuileB = null;
		Tuile tuileBG = null;
		Tuile tuileHG = null;
		Tuile tuileH = null;
		Tuile tuileHD = null;
		if((tuilePose.getNumero()%2) == 1){
			tuileHG = adjacent.get(0);
			tuileH = adjacent.get(1);
			tuileHD = adjacent.get(2);
			tuileBG = adjacent.get(3);
			tuileBD = adjacent.get(4);
			tuileB = adjacent.get(5);
		} else {
			tuileH = adjacent.get(0);
			tuileHG = adjacent.get(1);
			tuileHD = adjacent.get(2);
			tuileBG = adjacent.get(3);
			tuileB = adjacent.get(4);
			tuileBD = adjacent.get(5);
		}

	/* Si la tuile est visible */
		if(tuileBD.getVisible()){
			/* Je récupère la couleur Haut Gauche de la tuile en bas à droite */
			Color[] colors = tuileBD.getColors();
			Color colorHG = colors[3];
			/* Si même couleur, vérifie si la tuileBD fait partie d'une poche dont la couleur est celle qu'on vient de vérifier */
			if(maTuileColors[0].equals(colorHG)){
				int[] pochesId = tuileBD.getPocheId();
				/* Si la tuile n'a pas encore de poche, alors créons une poche */
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorHG, countPoche);
					po.addTuileInPoche(tuileBD);
					tuilePose.setPoche(countPoche);
					tuileBD.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				/* Par contre si elle a déjà une poche, il faut récupérer l'ID de la poche qui a comme couleur celle qu'on vient de vérifier */
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorHG)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorHG)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorHG, countPoche);
						po.addTuileInPoche(tuileBD);
						tuilePose.setPoche(countPoche);
						tuileBD.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}

		}
		if(tuileB.getVisible()){
			Color[] colors = tuileB.getColors();
			Color colorH = colors[4];

			if(maTuileColors[1].equals(colorH)){
				int[] pochesId = tuileB.getPocheId();
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorH, countPoche);
					po.addTuileInPoche(tuileB);
					tuilePose.setPoche(countPoche);
					tuileB.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorH)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorH)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorH, countPoche);
						po.addTuileInPoche(tuileB);
						tuilePose.setPoche(countPoche);
						tuileB.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}
	
		}
		if(tuileBG.getVisible()){
        	Color[] colors = tuileBG.getColors();
			Color colorHD = colors[5];

			if(maTuileColors[2].equals(colorHD)){
				int[] pochesId = tuileBG.getPocheId();
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorHD, countPoche);
					po.addTuileInPoche(tuileBG);
					tuilePose.setPoche(countPoche);
					tuileBG.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorHD)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorHD)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorHD, countPoche);
						po.addTuileInPoche(tuileBG);
						tuilePose.setPoche(countPoche);
						tuileBG.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}
    	}
		if(tuileHG.getVisible()){
        	Color[] colors = tuileHG.getColors();
			Color colorBD = colors[0];

			if(maTuileColors[3].equals(colorBD)){
				int[] pochesId = tuileHG.getPocheId();
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorBD, countPoche);
					po.addTuileInPoche(tuileHG);
					tuilePose.setPoche(countPoche);
					tuileHG.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorBD)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorBD)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorBD, countPoche);
						po.addTuileInPoche(tuileHG);
						tuilePose.setPoche(countPoche);
						tuileHG.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}

    	}
		if(tuileH.getVisible()){
        	Color[] colors = tuileH.getColors();
			Color colorB = colors[1];

			if(maTuileColors[4].equals(colorB)){
				int[] pochesId = tuileH.getPocheId();
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorB, countPoche);
					po.addTuileInPoche(tuileH);
					tuilePose.setPoche(countPoche);
					tuileH.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorB)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorB)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorB, countPoche);
						po.addTuileInPoche(tuileH);
						tuilePose.setPoche(countPoche);
						tuileH.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}

    	}
		if(tuileHD.getVisible()){
        	Color[] colors = tuileHD.getColors();
			Color colorBG = colors[2];

			if(maTuileColors[5].equals(colorBG)){
				int[] pochesId = tuileHD.getPocheId();
				if(pochesId[0] == 0 && pochesId[1] == 0){
					Poche po = new Poche(tuilePose, colorBG, countPoche);
					po.addTuileInPoche(tuileHD);
					tuilePose.setPoche(countPoche);
					tuileHD.setPoche(countPoche);
					this.poches.add(po);
					countPoche++;
				} else {
					int correctPoche = 0;
					for(Poche poche: this.poches){
						if(pochesId[0] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorBG)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(pochesId[1] == poche.getIdPoche()){
							if(poche.getColorPoche().equals(colorBG)){
								correctPoche = poche.getIdPoche();
							}
						}
						if(correctPoche > 0){
							poche.addTuileInPoche(tuilePose);
							tuilePose.setPoche(poche.getIdPoche());
						}
					}
					if(correctPoche == 0){
						Poche po = new Poche(tuilePose, colorBG, countPoche);
						po.addTuileInPoche(tuileHD);
						tuilePose.setPoche(countPoche);
						tuileHD.setPoche(countPoche);
						this.poches.add(po);
						countPoche++;
					}
				}
			}
    	}

		addScore();

    }

    /**
     * Met à jour le score
     */

	public void addScore(){
		this.score = 0;
		for(Poche poche: this.poches){
			int taille = poche.getSize();			
			this.score = this.score + Math.pow(taille, 2);
		}
		for(Tuile tuile: this.tuiles){
			int[] pochesId = tuile.getPocheId();

			if((pochesId[0] == 0 && pochesId[1] == 0) && tuile.getVisible()){
				this.score = this.score + Math.pow(1, 2);
			}
		}


	}













}
 
 
 
 
 
 
 
 
