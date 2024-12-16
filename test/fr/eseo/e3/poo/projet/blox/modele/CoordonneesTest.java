package fr.eseo.e3.poo.projet.blox.modele;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

public class CoordonneesTest {
	private Coordonnees coordonnees;
	
	@Test
	void TestCoordonnees(){
		Coordonnees coordonnees = new Coordonnees(4, 2);
		System.out.println(coordonnees);
	}
	@Test
    void testToString() {
        String expected = "Coordonnees{abscisse=5, ordonnee=10}";
        assertEquals(expected, coordonnees.toString(), "La représentation en chaîne doit correspondre au format attendu");
    }
	@Test
    void testEquals() {
        Coordonnees c = new Coordonnees(5, 10);
        assertTrue(coordonnees.equals(c), "Deux coordonnées avec les mêmes valeurs doivent être égales");
    }
}


