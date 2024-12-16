package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Element {
	
	private Couleur couleur = Couleur.ROUGE;
	private Coordonnees coordonnees;

	public Element(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
		
	}
	
	public Element(int abscisse, int ordonnee) {
		this.coordonnees = new Coordonnees(abscisse,ordonnee);

	}
	
	@Override
	public String toString() {
		return this.coordonnees + " - " + this.couleur ;
	}

	public Element(int abscisse, int ordonnee, Couleur couleur) {
		this.coordonnees = new Coordonnees(abscisse,ordonnee);
		this.couleur = couleur;
	}
	
	public Element(Coordonnees coordonnees, Couleur couleur) {
		this.coordonnees = coordonnees;
		this.couleur = couleur;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public int hashCode() {
		return Objects.hash(coordonnees, couleur);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Element))
			return false;
		Element other = (Element) obj;
		return Objects.equals(coordonnees, other.coordonnees) && couleur == other.couleur;
	}

	public Coordonnees getCoordonnees() {
		return this.coordonnees;
	}
	public void deplacerDe(int deltaX, int deltaY) {
	        int nouvelleAbscisse = coordonnees.getAbscisse() + deltaX;
	        int nouvelleOrdonnee = coordonnees.getOrdonnee() + deltaY;
	        coordonnees.setAbscisse(nouvelleAbscisse);
	        coordonnees.setOrdonnee(nouvelleOrdonnee);
	    }
}

	