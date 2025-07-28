/**
 * Classe répresentant une poche
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */


package fr.iutfbleau.Vues;
import fr.iutfbleau.Controllers.*;
import fr.iutfbleau.Models.*;
import java.awt.*;
import java.util.ArrayList;

public class Poche {
	private ArrayList<Tuile> tuilesInPoche;
	private Color myColor;
	private int idPoche;


	/**
	 * Constructeur de la classe
	 * @param t tuile 
	 * @param c couleur de la poche
	 * @param id id de la poche
	 */

	public Poche(Tuile t, Color c, int id){
		this.tuilesInPoche = new ArrayList<>();
		this.tuilesInPoche.add(t);
		this.myColor = c;
		this.idPoche = id;
	}
	
	/**
	 * Ajouter une tuile à la poche
	 * @param t tuile à ajouter 
	 */

	public void addTuileInPoche(Tuile t){
		this.tuilesInPoche.add(t);
	}

	/**
	 * Obtenir la couleur de la poche
	 * @return la couleur de la poche
	 */

	public Color getColorPoche(){
		return this.myColor;
	}

	/**
	 * Obtenir l'id de la poche
	 * @return l'id de la poche
	 */

	public int getIdPoche(){
		return this.idPoche;
	}

	/**
	 * Dégugage
	 */

	public void afficherPoche(){
		System.out.println("Je suis la poche n°" + this.idPoche);
		System.out.println("J'ai la couleur : " + this.myColor);
		System.out.println("Voici la liste des tuiles qui sont dans ma poche");
		for(Tuile tuile: this.tuilesInPoche){
			System.out.println("Tuile n°" + tuile.getNumero());
		}

		System.out.println("------------------------------------------");
	}

	/**
	 * Obtenir la taille de la poche
	 * @return taille de la poche
	 */

	public int getSize(){
		return this.tuilesInPoche.size();
	}
}





















