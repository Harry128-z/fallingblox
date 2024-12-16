package fr.eseo.e3.poo.projet.blox.vue;


import org.junit.jupiter.api.Test;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.UsineDePiece;

public class VuePuitsAffichageTest {
    
    @Test
    private void testConstructeurPuits() {
        UsineDePiece.genererTetromino();
        Puits puits = new Puits();
        VuePuits vuePuits = new VuePuits(puits);
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Puits");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(vuePuits);
            frame.pack();
            frame.setLocationRelativeTo(null);	
            frame.setVisible(true);
        });
        
        puits.addPropertyChangeListener(vuePuits); 
    }
    
    @Test
    private void testConstructeurPuitsTaille() {
        Puits puits = new Puits();
        int taille = 50;
        VuePuits vuePuits = new VuePuits(puits, taille);
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Puits et taille");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(vuePuits);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
        
        puits.addPropertyChangeListener(vuePuits); 
    }
  

    
    public static void main (String [] args) {
        
        VuePuitsAffichageTest t = new VuePuitsAffichageTest();
        t.testConstructeurPuits();
        t.testConstructeurPuitsTaille();
        
    }     
}

