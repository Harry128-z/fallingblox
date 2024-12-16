package fr.eseo.e3.poo.projet.blox.vue;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import fr.eseo.e3.poo.projet.blox.controleur.PieceDeplacement;
import fr.eseo.e3.poo.projet.blox.controleur.PieceRotation;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VuePuits extends JPanel implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TAILLE_PAR_DEFAUT = 30;
	private Puits puits;
	private int taille;
	private VuePiece vuePiece;
	private PieceDeplacement pieceDeplacement;
	private PieceRotation pieceRotation;

	public VuePuits(Puits puits) {
		this(puits, TAILLE_PAR_DEFAUT);
	}

	public VuePuits(Puits puits, int taille) {
		pieceDeplacement = new PieceDeplacement(this);
		pieceRotation = new PieceRotation(this);
		this.setPuits(puits);
		setTaille(taille);
	

	}

	public Puits getPuits() {
		return puits;
	}

	public int getTaille() {
		return taille;
	}

	public void setPuits(Puits puits) {

		if (this.puits != null) {
			this.puits.removePropertyChangeListener(this);

		}
		// setPreferredSize(new Dimension(taille,taille));
		if (puits != null) {
			this.puits = puits;
			this.puits.addPropertyChangeListener(this);
			setPreferredSize(new Dimension(taille * puits.getLargeur(), taille * puits.getProfondeur()));
		} else {
			setPreferredSize(new Dimension(taille, taille));

		}
		if (pieceDeplacement != null) {
			pieceDeplacement.setPuits(puits);
			this.removeMouseListener(pieceDeplacement);
			this.removeMouseMotionListener(pieceDeplacement);
			this.removeMouseWheelListener(pieceDeplacement);
		}
		if (pieceRotation != null) {
			pieceRotation.setPuits(puits);
			this.removeMouseListener(pieceRotation);
			this.removeMouseMotionListener(pieceRotation);
			this.removeMouseWheelListener(pieceRotation);
		}
	// deplacement
		this.addMouseListener(pieceDeplacement);
		this.addMouseMotionListener(pieceDeplacement);
		this.addMouseWheelListener(pieceDeplacement);
		
		// rotation
		this.addMouseListener(pieceRotation);
		this.addMouseMotionListener(pieceRotation);
		this.addMouseWheelListener(pieceRotation);
	}

	private void setVuePiece(VuePiece vuePiece) {
		this.vuePiece = vuePiece;
	}

	public VuePiece getVuePiece() {
		return vuePiece;
	}

	public void setTaille(int taille) {
		this.taille = taille;
		setPreferredSize(new Dimension(taille * puits.getLargeur(), taille * puits.getProfondeur()));
	}

	public void propertyChange(PropertyChangeEvent evt) {
		// System.out.println("!!!");
		if (evt.getPropertyName().equals(Puits.MODIFICATION_PIECE_ACTUELLE)) {
			// System.out.println("???");
			setVuePiece(new VuePiece((Piece) evt.getNewValue(), getTaille()));
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g.create();
		g2D.setColor(Color.WHITE);
		g2D.fillRect(0, 0, puits.getLargeur() * taille, puits.getProfondeur() * taille);
		g2D.setColor(Color.LIGHT_GRAY);

		for (int i = 0; i < puits.getLargeur(); i++) {
			for (int j = 0; j < puits.getProfondeur(); j++) {
				g2D.drawRect(i * taille, j * taille, taille, taille);
			}
		}
		if (vuePiece != null) {
			vuePiece.afficherPiece(g2D);
		}
		g2D.dispose();
	}
}
