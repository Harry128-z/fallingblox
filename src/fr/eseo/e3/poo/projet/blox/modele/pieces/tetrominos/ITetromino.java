package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;

public class ITetromino extends Tetromino {

	public ITetromino(Coordonnees coordonnees, Couleur couleur) {
		super(coordonnees, couleur);
		
	}
	
	protected void setElements(Coordonnees coordonnees, Couleur couleur) {
		getElements()[0] = new Element(new Coordonnees(coordonnees.getAbscisse(), coordonnees.getOrdonnee()), couleur);
		getElements()[1] = new Element(new Coordonnees(coordonnees.getAbscisse(), coordonnees.getOrdonnee()-1), couleur);
		getElements()[2] = new Element(new Coordonnees(coordonnees.getAbscisse(), coordonnees.getOrdonnee()-2), couleur);
		getElements()[3] = new Element(new Coordonnees(coordonnees.getAbscisse(), coordonnees.getOrdonnee()+1), couleur);	 
	}
}