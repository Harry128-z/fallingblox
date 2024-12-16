package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;

public class OTetrominoTest {


	@Test
	void testOTetrominoTest(){
		Coordonnees coordonnees = new Coordonnees(0,0);
		Couleur couleur = Couleur.BLEU;
		OTetromino oTetromino = new OTetromino(coordonnees, couleur);
		assertEquals(4, oTetromino.getElements().length);
		assertNotNull(oTetromino.getElements());
		String expectedString = "OTetromino: [OTetromino :\r\n"
				+ "(6, 5) - CYAN\r\n"
				+ "(7, 5) - CYAN\r\n"
				+ "(6, 4) - CYAN\r\n"
				+ "(7, 4) - CYAN";
		assertEquals(expectedString, oTetromino.toString());
	}

}
