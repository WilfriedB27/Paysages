/**
 * Class pour intialiser la connection à la base de données 
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
*/

package fr.iutfbleau.Models;
import fr.iutfbleau.Vues.*;
import fr.iutfbleau.Models.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    /**
     * Attribut servant à la connection de la base de données
     */

    private static final String url = "jdbc:mariadb://dwarves.iut-fbleau.fr/bridja";
    private static final String user = "bridja";
    private static final String password = "123456";


    /**
     * Établi la connection à la base de données
     * @return la connection à la base de données
     */

    public static Connection getConnection() {
        Connection connection = null;
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        }catch (ClassNotFoundException e){
            System.err.println("Erreur de changement du driver JDBC: " + e.getMessage());

        }catch(SQLException e){
            System.err.println("Erreur de connection à la base de donnée: " + e.getMessage());

        }catch( Exception e){
            System.err.println("Erreur inattendue: " + e.getMessage());

        }
        return connection;

    }

}
