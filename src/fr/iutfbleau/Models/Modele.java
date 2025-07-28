/**
 * Class pour gérer les requêtes SQL vers la base de données  
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Models;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Controllers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;


public class Modele {

	/**
	 * Chaine de caractère de la série choisi dans le menu
	 */

    private String seriesSelectionne;

    /**
     * Connection de la base de données
     */

    private Connection connection;

    /**
     * Constructeur de la classe
     */

    public Modele(){
        this.connection = DatabaseConnection.getConnection();
        this.seriesSelectionne = "Serie 1";
    }

    /**
     * Enregistre la série choisi dans le menu
     * @param serie la série choisi dans le menu
     */

    public void setSerieSelectionnee(String serie){
        this.seriesSelectionne = serie;

        /*Enregistrer la série sélectionnee dans la base de données */
        enregistrerSerieSectionnee(serie);
    }

    /**
     * Obtenir la série selectionnée 
     * @return le nom de la série choisi
     */

    public String getSerieSelectionnee(){
        return seriesSelectionne;
    }

    /**
     * Enregistre la série choisi dans le menu dans la base de données
     * @param serie nom de la série choisi
     */

    private void enregistrerSerieSectionnee(String serie){
        try{
            /*Obtenir Id de la table Series */
            String query = "SELECT id FROM Series WHERE nom = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, serie);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int idSerie = resultSet.getInt("id");

                /*String insertQuery = "INSERT INTO Scores (score, id_serie) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1,0);
                insertStatement.setInt(2, idSerie);
                insertStatement.executeUpdate();*/
            }        
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Ferme la connection à la base de données
     */

    public void fermerConnection(){
        if(connection != null){
            try{
                connection.close();

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Renvoie tous les scores de la série à laquelle on joue
     * @param serie serie choisi dans le menu
     * @return le tableau des score d'une série
     */

	public int[] getScoresForSerie(String serie){
		int id_serie = getIdOfSerie(serie);
		int[] score_tab = new int[100];
		try {

			PreparedStatement pst = connection.prepareStatement(
			"SELECT score FROM Scores WHERE id_serie = ?");
			pst.setInt(1, id_serie);
			ResultSet rs = pst.executeQuery();

			for(int i = 0; rs.next(); i++){
				score_tab[i] = rs.getInt(1);
			}

			return score_tab;

		} catch(SQLException e){

			e.printStackTrace();
			return new int[0];
		}
	}

	/**
	 * Ajoute le score du joueur à la base de données
	 * @param scoreJoueur score que le joueur vient de réaliser en jouant
	 * @param serie serie choisi dans le menu
	 */

	public void ajouterScore(int scoreJoueur, String serie){
		int id_serie = getIdOfSerie(serie);

		try {

			PreparedStatement pst = connection.prepareStatement("INSERT INTO Scores (score, id_serie) VALUES (?, ?)");
			pst.setInt(1,scoreJoueur);
			pst.setInt(2, id_serie);
			pst.executeUpdate();
			return;

		} catch(SQLException e) {
		   	e.printStackTrace();
			return;
		}
	}

	/**
	 * Renvoie l'Id de la série choisi dans le menu
	 * @param serie serie choisi dans le menu
	 * @return Id de la série choisi dans le menu
	 */

    public int getIdOfSerie(String serie){
	    try {
		PreparedStatement pst = connection.prepareStatement("SELECT id FROM Series WHERE nom = ?");
		pst.setString(1, this.seriesSelectionne);
		ResultSet res = pst.executeQuery();
		while(res.next()){
			return res.getInt(1);
		}
		return 0;
	    } catch(SQLException e){
	   		e.printStackTrace();
			return 0;
	    }	
    }


    /**
     * Renvoie la couleur d'une tuile
     * @param idSerie id la serie choisi
     * @param pos position de la tuile
     * @return la première couleur d'une tuile
     */

    public Color getFirstColor(int idSerie, int pos){
    	try {
			String element = "";
			
			PreparedStatement pst = connection.prepareStatement("SELECT Tuile.nom FROM Terrain JOIN Series ON Series.id = Terrain.id_serie JOIN Tuile ON Tuile.id = Terrain.id_tuile1 WHERE Terrain.id_serie = ? AND Terrain.position = ?");
			pst.setInt(1, idSerie);
			pst.setInt(2, pos);
			ResultSet res = pst.executeQuery();
			while(res.next()){
				element = res.getString(1);
			}


			if(element.equals("Mer")){
				Color c = new Color(0, 135, 255);
				return c;
			} else if(element.equals("Pré")){
				Color c = new Color(0, 213, 36);
				return c;
			} else if(element.equals("Champs")){
				Color c = new Color(226, 240, 0);
				return c;
			} else if(element.equals("Forêt")){
				Color c = new Color(0, 156, 7);
				return c;
			} else if(element.equals("Montagne")){
				Color c = new Color(111, 111, 111);
				return c;
			}
			Color c = new Color(0, 180, 0, 0);
			return c;

		} catch(SQLException e){
			e.printStackTrace();
			Color c = new Color(0, 180, 0, 0);
			return c;
		}
    }

    /**
     * Renvoie la deuxième couleur d'une tuile
     * @param idSerie id la serie choisi
     * @param pos position de la tuile
     * @return la deuxième couleur d'une tuile
     */

    public Color getSecondColor(int idSerie, int pos){
    	try {
			String element = "";
			PreparedStatement pst = connection.prepareStatement("SELECT Tuile.nom FROM Terrain JOIN Series ON Series.id = Terrain.id_serie JOIN Tuile ON Tuile.id = Terrain.id_tuile2 WHERE Terrain.id_serie = ? AND Terrain.position = ?");
			pst.setInt(1, idSerie);
			pst.setInt(2, pos);
			ResultSet res = pst.executeQuery();
			while(res.next()){
				element = res.getString(1);
			}
			if(element.equals("Mer")){
				Color c = new Color(0, 135, 255);
				return c;
			} else if(element.equals("Pré")){
				Color c = new Color(0, 213, 36);
				return c;
			} else if(element.equals("Champs")){
				Color c = new Color(216, 240, 0);
				return c;
			} else if(element.equals("Forêt")){
				Color c = new Color(0, 156, 7);
				return c;
			} else if(element.equals("Montagne")){
				Color c = new Color(111, 111, 111);
				return c;
			}
			Color c = new Color(0, 180, 0, 0);
			return c;
		} catch(SQLException e){
			e.printStackTrace();
			Color c = new Color(0, 180, 0, 0);
			return c;
		}
    }

    /**
     * Renvoie la première proportion de couleur d'une tuile
     * @param idSerie id la serie choisi
     * @param pos position de la tuile
     * @return la première proportion de couleur d'une tuile
     */

    public int getFirstProportion(int idSerie, int pos){
    	try {
			PreparedStatement pst = connection.prepareStatement("SELECT Terrain.ratio1 FROM Terrain WHERE Terrain.id_serie = ? AND Terrain.position = ?");
			pst.setInt(1, idSerie);
			pst.setInt(2, pos);
			ResultSet res = pst.executeQuery();
			while(res.next()){
				return res.getInt(1);
			}
			return 0;
	
		} catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
    
    }


    /**
     * Renvoie la deuxième proportion de couleur d'une tuile
     * @param idSerie id la serie choisi
     * @param pos position de la tuile
     * @return la deuxième proportion de couleur d'une tuile
     */

    public int getSecondProportion(int idSerie, int pos){
    	try {
			PreparedStatement pst = connection.prepareStatement("SELECT Terrain.ratio2 FROM Terrain WHERE Terrain.id_serie = ? AND Terrain.position = ?");
			pst.setInt(1, idSerie);
			pst.setInt(2, pos);
			ResultSet res = pst.executeQuery();
			while(res.next()){
				return res.getInt(1);
			}
			return 0;
		} catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
    }
}
