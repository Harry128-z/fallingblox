package fr.eseo.e3.poo.projet.blox.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ElementTest {

    private Element element;
    private final Coordonnees coordonnees = new Coordonnees(1, 1);
    private final Couleur couleur = Couleur.VIOLET;

    @Test
    	void testConstructeurCoordonneesCouleur() {
        assertEquals(coordonnees, element.getCoordonnees());
        assertEquals(couleur, element.getCouleur());
    }

    @Test
    	void testSetCoordonnees() {
        Coordonnees nouvellesCoordonnees = new Coordonnees(2, 2);
        element.setCoordonnees(nouvellesCoordonnees);
        assertEquals(nouvellesCoordonnees, element.getCoordonnees());
    }

    @Test
    	void testSetCouleur() {
        element.setCouleur(Couleur.ROUGE);
        assertEquals(Couleur.ROUGE, element.getCouleur());
    }

    @Test
    	void testToString() {
    	Element element = new Element(3, 15, Couleur.VIOLET);
        String expected = "(3, 15) - VIOLET]";
        assertEquals(expected, element.toString());
    }

    @Test
    	void testEquals() {
        Element autre = new Element(coordonnees, couleur);
        assertTrue(element.equals(autre));
    }

    
    
}
