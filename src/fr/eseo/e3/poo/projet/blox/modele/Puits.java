package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Puits  {
	public static final int LARGEUR_PAR_DEFAUT=5;
	public static final int  PROFONDEUR_PAR_DEFAUT=20;
	private int largeur;
	private int profondeur;
	private Piece pieceActuelle;
	private Piece pieceSuivante;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String MODIFICATION_PIECE_ACTUELLE="pieceActuelleM";
	public static final String MODIFICATION_PIECE_SUIVANTE="pieceSuivanteM";
	public Tas tas;
	
	public Puits() {
		this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
	}
	

	public Puits(int largeur, int profondeur) {
		this.setLargeur(largeur);
		this.setProfondeur(profondeur);
		this.pieceActuelle = null;
		this.pieceSuivante = null;
		 //pcs.addPropertyChangeListener(this);
		this.tas = new Tas(this);
	}
	public Puits(int largeur, int profondeur, int nbElements, int nbLignes) {
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.tas = new Tas(this, nbElements, nbLignes);
    }
	
	public int getLargeur() {
		return largeur;
	}
	public void setLargeur(int largeur) {	
		if(largeur<5 || largeur>15){
			 throw new IllegalArgumentException("Les dimensions du puits sont invalides.");
		}
		this.largeur = largeur;
	}
	public int getProfondeur() {
		return profondeur;
	}
	public void setProfondeur(int profondeur) {
		if(profondeur<15 || profondeur>25){
			 throw new IllegalArgumentException("Les dimensions du puits sont invalides.");
		}
		this.profondeur = profondeur;
	}
	
	public Piece getPieceActuelle() {
		return pieceActuelle;
	}
	
	public Piece getPieceSuivante() {
		return pieceSuivante;
	}
	
	public void setPieceSuivante(Piece piece) {
		if(this.pieceSuivante != null) {
			pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, pieceActuelle, pieceSuivante);
			pieceActuelle= pieceSuivante;
			int x = largeur/2;
			int y = -4;
			pieceActuelle.setPosition(x, y);
		}
		piece.setPuits(this);
		pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, pieceSuivante, piece);
		pieceSuivante = piece;
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	        pcs.addPropertyChangeListener(listener);
	    }
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	        pcs.removePropertyChangeListener(listener);
	    }
	
	public String toString(){
		String sb = "Puits : Dimension " + largeur + " x " + profondeur + "\n";
		sb+= "Piece Actuelle : ";
		if(pieceActuelle != null) 
        		sb+= pieceActuelle.toString();
		else sb+= "<aucune>\n";
		sb += "Piece Suivante : ";
		if(pieceSuivante != null)
			sb+= pieceSuivante.toString();
		else
			sb += "<aucune>\n";
		return sb;
    }
	public Tas getTas() {
		return tas;
	}
	public void setTas(Tas tas) {
		this.tas = tas;
	}

	
		
}
	



