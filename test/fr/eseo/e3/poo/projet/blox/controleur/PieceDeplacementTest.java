package fr.eseo.e3.poo.projet.blox.controleur;
import javax.swing.JFrame;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class PieceDeplacementTest {

	 public static void main(String[] args) {
	        Puits puits = new Puits(10, 20); 
	        JFrame frame = new JFrame("Test de déplacement de pièce");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        VuePuits vuePuits = new VuePuits(puits);
	        frame.getContentPane().add(vuePuits);
	        vuePuits.addMouseListener(new PieceDeplacement(vuePuits));
	        frame.pack();
	        frame.setVisible(true);
	        vuePuits.addMouseWheelListener(e -> {
	            int wheelRotation = e.getWheelRotation();
	            if (wheelRotation > 0) {
						puits.getPieceActuelle().deplacerDe(0, 1);
	            }
	        });
	        vuePuits.repaint();
	    }
	}


