package fr.eseo.e3.poo.projet.blox.modele.pieces;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;

import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;



public class UsineDePieceTest {

	

	    @Test
	    void testGenererTetromino_AleatoireComplet() {
	        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_COMPLET);
	        Tetromino tetro = UsineDePiece.genererTetromino();
	        assertNotNull(tetro);
	    }

	    @Test
	    void testGenererTetromino_AleatoirePiece() {
	        UsineDePiece.setMode(UsineDePiece.ALEATOIRE_PIECE);
	        Tetromino tetro = UsineDePiece.genererTetromino();
	        assertNotNull(tetro);
	    }

	    @Test
	    void testGenererTetromino_Cyclic() {
	        UsineDePiece.setMode(UsineDePiece.CYCLIC);
	        Tetromino tetro = UsineDePiece.genererTetromino();
	        assertNotNull(tetro);
	    }
	}


