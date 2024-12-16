package fr.eseo.e3.poo.projet.blox.modele.pieces;
import  java.util.Random;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.Tetromino;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;

public class UsineDePiece {
	public static final int ALEATOIRE_COMPLET = 1;
	public static final int ALEATOIRE_PIECE = 2;
	public static final int CYCLIC = 0;
	public static int modeParDefaut = ALEATOIRE_PIECE;
	private static int cycle;
	
	private UsineDePiece() {}
	
	public static void setMode(int mode) {
		UsineDePiece.modeParDefaut = mode;
	}
	
	public static Tetromino genererTetromino() {
		Tetromino tetro = null;
		
		switch(modeParDefaut){
			case CYCLIC :
				if (cycle==0)
				{
					tetro = new OTetromino(new Coordonnees(2,3),Couleur.ROUGE);
					cycle=1;
				}
				else
				{
					tetro = new ITetromino(new Coordonnees(2,3),Couleur.ORANGE);
					cycle=0;
				}
				
				break;
			case ALEATOIRE_COMPLET :
				Random rand = new Random();
				int r = rand.nextInt(1);
	        	if(r==1) {
	        		tetro = new ITetromino(new Coordonnees(3,2),Couleur.ORANGE);
	        	}
	        	else {
	        		 tetro = new OTetromino(new Coordonnees(2,3),Couleur.ROUGE);
	        	}
	            break;
	        case ALEATOIRE_PIECE:
	        	tetro = new ITetromino(new Coordonnees(2,3),Couleur.ORANGE);
	        	break;
	        default:
	            break;
			}
			return tetro;
		}
			
			
	}

