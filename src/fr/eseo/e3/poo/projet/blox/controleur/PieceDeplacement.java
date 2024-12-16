package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

public class PieceDeplacement extends MouseAdapter implements java.awt.event.MouseMotionListener {
	private Puits puits;
	private VuePuits vuePuits;
	private int derniereColonne;

	public PieceDeplacement(VuePuits vuePuits) {
		this.vuePuits = vuePuits;
		this.puits = vuePuits.getPuits();
		this.derniereColonne = -1;
	}

// antiplagiat yann Nzelong(methode mouseMoved)
	public void mouseMoved(MouseEvent e) {
		if (puits.getPieceActuelle() != null) {
			int colonneSouris = e.getX() / vuePuits.getTaille();
			if (this.derniereColonne == -1) {
				this.derniereColonne = colonneSouris;
			} else if (colonneSouris != this.derniereColonne) {
				int a = colonneSouris - this.derniereColonne;
				if (a > 0) {
						puits.getPieceActuelle().deplacerDe(1, 0); 
				}
				if (a < 0) {
						puits.getPieceActuelle().deplacerDe(-1, 0);
				}
				derniereColonne = colonneSouris;
				vuePuits.repaint();
			}
		}
		
	}

	public void mouseEntered(MouseEvent e) {

		derniereColonne = -1;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {

		if (puits.getPieceActuelle() != null) {
			if (e.getWheelRotation() > 0) {
				puits.getPieceActuelle().deplacerDe(0, 1);
					
				}
		}
		vuePuits.repaint();
	}

	public void mouseDragged(MouseEvent e) {

	}

	public Puits getPuits() {
		return puits;
	}

	public void setPuits(Puits puits) {
		this.puits = puits;
	}

	public VuePuits getVuePuits() {
		return vuePuits;
	}

	public void setVuePuits(VuePuits vuePuits) {
		this.vuePuits = vuePuits;
	}

}
