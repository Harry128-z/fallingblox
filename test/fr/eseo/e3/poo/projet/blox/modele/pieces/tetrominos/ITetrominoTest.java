package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;

public class ITetrominoTest {

	 @Test
	     void testITetromino() {
	        Coordonnees coordonnees = new Coordonnees(0,0);
	        Couleur couleur = Couleur.ROUGE;
	        ITetromino iTetromino = new ITetromino(coordonnees, couleur);
	        
	        assertNotNull(iTetromino.getElements());
	        assertEquals(4, iTetromino.getElements().length);
	    }
	    
	    @Test
	    public void testSetElements() {
	        Coordonnees coordonnees = new Coordonnees(0,0); 
	        Couleur couleur = Couleur.ROUGE;
	        ITetromino iTetromino = new ITetromino(coordonnees, couleur);
	        
	 
	        iTetromino.setElements(coordonnees, couleur);
	        assertNotNull(iTetromino.getElements());
	        assertEquals(4, iTetromino.getElements().length);
	       
	    }
}
	

