package fr.eseo.e3.poo.projet.blox.vue;


import java.awt.Color;
import java.awt.Graphics2D;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public class VuePiece {
public static final double  MULTIPLIER_TEINTE=0.3;
	private final int taille;
	private final Piece piece;
	
	public VuePiece(Piece piece, int taille) {
        this.piece = piece;
        this.taille = taille;
    }

    public static Color teinte(Color couleur) {
        int r = (int) (couleur.getRed()+(255-couleur.getRed()) * MULTIPLIER_TEINTE);
        int g = (int) (couleur.getGreen()+(255-couleur.getGreen()) * MULTIPLIER_TEINTE);
        int b = (int) (couleur.getBlue()+(255-couleur.getBlue())* MULTIPLIER_TEINTE);
       
        
        return new Color(r, g, b);
    }

    public void afficherPiece(Graphics2D g2D) {
        Color couleur = piece.getElements()[0].getCouleur().getCouleurPourAffichage();
        Color couleur2 = teinte(couleur);
        
        for (Element element : piece.getElements()) {
        	if(element==piece.getElements()[0])
        		g2D.setColor(couleur2);
        	else
        		g2D.setColor(couleur);
            g2D.fill3DRect(element.getCoordonnees().getAbscisse() * taille, 
            				element.getCoordonnees().getOrdonnee() * taille, 
            				taille, taille, true);
        }
    }
  
    

}
