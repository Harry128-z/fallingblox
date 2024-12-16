package fr.eseo.e3.poo.projet.blox.controleur;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;



public class PieceRotation extends  java.awt.event.MouseAdapter {
	private VuePuits vuePuits;
	private Puits puits;
	
	public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.puits=vuePuits.getPuits();
    }

    public VuePuits getVuePuits() {
		return vuePuits;
	}

	public void setVuePuits(VuePuits vuePuits) {
		this.vuePuits = vuePuits;
	}

	public Puits getPuits() {
		return puits;
	}

	public void setPuits(Puits puits) {
		this.puits = puits;
	}

    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            try {
                vuePuits.getPuits().getPieceActuelle().tourner(true); 
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur lors de la rotation dans le sens horaire : " + e.getMessage());
            }
        } else if (SwingUtilities.isLeftMouseButton(event)) {
            try {
                vuePuits.getPuits().getPieceActuelle().tourner(false);
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur lors de la rotation dans le sens anti-horaire : " + e.getMessage());
            }
        }
        vuePuits.repaint();
    }
}