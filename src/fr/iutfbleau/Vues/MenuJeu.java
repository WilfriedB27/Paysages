/**
 * La classe MenuJeu est utilisée pour afficher la fenetre du menu ainsi que celle du jeu  
 * @author BRIGITTE Wilfried 
 * @author BRIDJA Abed 
 * @author DUBREUIL Christopher
 */

package fr.iutfbleau.Vues;
import fr.iutfbleau.Controllers.*;
import fr.iutfbleau.Models.*;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class MenuJeu extends JFrame{
    private JComboBox<String> comboBoxSeries;
    private JButton boutonjouer;
    private Modele modele;
    
    public MenuJeu (Modele m){
		this.modele = m;
        ImageIcon imageIcon = new ImageIcon("build/fr/iutfbleau/res/New_Game.jpg");
       
       	Image image = imageIcon.getImage().getScaledInstance(1400, 700, Image.SCALE_SMOOTH);
       	ImageIcon imageReduite = new ImageIcon(image);
        // Ajouter l'image redimensionnée à un JLabel
       	JLabel imageLabel = new JLabel(imageReduite);
       	setLayout(new BorderLayout());
       	add(imageLabel, BorderLayout.CENTER);

     	boutonjouer = new JButton("Jouer");
        boutonjouer.setBounds(80,320, 180,60);
        boutonjouer.setBackground(Color.BLACK);
        boutonjouer.setForeground(Color.ORANGE);

       
        /*Faire menu déroulant */

        String[] series = {"Serie 1", "Serie 2", "Serie 3"};
     	comboBoxSeries = new JComboBox<>(series);
    	comboBoxSeries.setBounds(80,490,180,60);
    	comboBoxSeries.setBackground(Color.BLACK);
        comboBoxSeries.setForeground(Color.ORANGE);

     	imageLabel.setLayout(null);
     	imageLabel.add(boutonjouer);
       	imageLabel.add(comboBoxSeries);
        


 	 	add(imageLabel);       

     	setTitle("Menu");
     	pack();
  	 	setSize(imageReduite.getIconWidth(), imageReduite.getIconHeight());
  	 	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	setLocationRelativeTo(null);
      	setVisible(true);

	
		/* Ajout des actionListener */
		this.comboBoxSeries.addActionListener(new SerieSelectionListener(this));
		this.boutonjouer.addActionListener(new JouerButtonListener(this));
    }


    /**
     * Obtenir la ComboBoxSeries
     * @return ComboBoxSeries
     */

    public JComboBox<String> getComboBoxSeries(){
        return comboBoxSeries;
    }

    /**
     * Obtenir le bouton jouer
     * @return le bouton jouer
     */

    public JButton getBoutonJouer(){
            return boutonjouer;
    }

    /**
     * Met à jour la série choisi dans le menu
     * @param serie nom de la serie choisi dans le menu
     */

    public void updateSerieSelectionnee(String serie){
    	this.modele.setSerieSelectionnee(serie);
	System.out.println("Série sélectionnée : " + serie);
    }


    /**
     * Lance le jeu de tuile
     */

    public void lancerJeu(){
    	String serieSelectionnee = this.modele.getSerieSelectionnee();
		if(serieSelectionnee == null || serieSelectionnee.isEmpty()){
			JOptionPane.showMessageDialog(this, "Aucune série sélectionnée !", "Erreur", JOptionPane.ERROR_MESSAGE);
			return ;
		}

		this.getContentPane().removeAll();
		this.getContentPane().setLayout(new BorderLayout());


		
		JLabel label = new JLabel("Vous jouez à la série " + serieSelectionnee);
		label.setFont(new Font("Arial", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		//panneauJeu.add(label, BorderLayout.NORTH);
		
		Terrain ter = new Terrain(this.modele, this,this);
		ter.setLayout(new BorderLayout());
		ter.add(label, BorderLayout.NORTH);
		Color c = new Color(200, 50, 50);
		ter.setBackground(c);

		//JPanel panneauScore = new JPanel();
		//panneauScore.setLayout(new BorderLayout());
		//panneauScore.setBackground(Color.BLACK);
		//panneauScore.setPreferredSize(new Dimension(getWidth(), 100));

		//JLabel intScore = new JLabel("Score : 0");
		//intScore.setForeground(Color.WHITE);
		//intScore.setFont(new Font("Arial", Font.BOLD, 30));
		//panneauScore.add(intScore, BorderLayout.WEST);

		//ter.add(panneauScore, BorderLayout.SOUTH);
		
		ter.setVisible(true);
		ter.revalidate();
		ter.repaint();


		this.add(ter);
		this.pack();
		this.setVisible(true);
		this.setSize(1400, 700);
		this.setLocationRelativeTo(null);
		this.revalidate();
		this.repaint();
    }
	public void showFin(int scoreJoueur, int[] scoreBD, Modele modele, String serie) {
		this.dispose();
        new Fin(scoreJoueur, scoreBD, modele, serie);
    }

	
	
}
