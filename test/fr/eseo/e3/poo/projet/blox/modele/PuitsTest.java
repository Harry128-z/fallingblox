package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class PuitsTest {
	
	 public void testPuits() {
	        int largeur = 10;
	        int profondeur = 20;
	        
	        Puits puits = new Puits(largeur, profondeur);
	        assertEquals(largeur, puits.getLargeur());
	        assertEquals(profondeur, puits.getProfondeur());
	        assertNull(puits.getPieceActuelle());
	        assertNull(puits.getPieceSuivante());

	        int nouvelleLargeur = 12;
	        puits.setLargeur(nouvelleLargeur);
	        assertEquals(nouvelleLargeur, puits.getLargeur());
	 }
	
	 public void testSetProfondeur_Invalid() {
        Puits puits = new Puits();
        int invalidProfondeur = 26;

        puits.setProfondeur(invalidProfondeur);
    }
	
	 void testSetLargeur_Invalid() {
	        Puits puits = new Puits();
	        int invalidLargeur = 4;

	        puits.setLargeur(invalidLargeur);
	    }
	 

}