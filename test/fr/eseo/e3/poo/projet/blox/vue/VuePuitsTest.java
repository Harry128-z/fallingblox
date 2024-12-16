package fr.eseo.e3.poo.projet.blox.vue;

import static org.junit.jupiter.api.Assertions.assertNotNull;



import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Puits;

public class VuePuitsTest {

	  public void testConstructeurPuits() {
	        Puits puits = new Puits();
	        VuePuits vuePuits = new VuePuits(puits);
	        
	        JFrame frame = new JFrame("Puits");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().add(vuePuits);
	        frame.pack();
	        frame.setLocationRelativeTo(null); 
	        frame.setVisible(true);
	        
	    }
	    
	    @Test
	    public void testConstructeurPuitsTaille() {
	        Puits puits = new Puits();
	        int taille = 50;
	        VuePuits vuePuits = new VuePuits(puits, taille);
	        
	        JFrame frame = new JFrame("Puits et taille");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().add(vuePuits);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        
	        assertNotNull(frame);
	    }
	}
