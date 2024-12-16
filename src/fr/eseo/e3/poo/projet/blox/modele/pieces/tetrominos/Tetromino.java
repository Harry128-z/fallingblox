package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public abstract class Tetromino implements Piece {
		private Element[] elements;
		private Puits puits;
		public Tetromino(Coordonnees coordonnees, Couleur couleur){
			elements = new Element[4];
			setElements(coordonnees, couleur);
		}
		
		public Element[]getElements(){
			return elements;
		}
		protected abstract void setElements(Coordonnees coordonnees, Couleur couleur);
		
		public void setPosition(int abscisse, int ordonnee) {
			Coordonnees coordonnees = new Coordonnees(abscisse, ordonnee);
			setElements(coordonnees, this.elements[0].getCouleur());
		}
		
		public String toString(){
			String sb = super.getClass().getSimpleName() + " :\n";
			for(Element element : elements) {
				sb += "\t" + element + "\n";	
			}
			return sb;
			
		}
		public Puits getPuits() {
			return puits;
		}
		
		public void setPuits(Puits puits) {
			this.puits=puits;
		}
		public void verifierDeplacement1(int abscisse1, int ordonnee1) throws BloxException {
		    Puits puitsActuel = this.getPuits();
		    if (puitsActuel == null) {
		        return;
		    }
		    
		    if (abscisse1 >= puitsActuel.getLargeur() || ordonnee1 < 0) {
		        throw new BloxException("Déplacement hors des limites horizontales du puits",
		                BloxException.BLOX_SORTIE_PUITS);
		    }
		    
		    if (ordonnee1 >= puitsActuel.getProfondeur()) {
		        throw new BloxException("Déplacement au-delà du fond du puits", BloxException.BLOX_COLLISION);
		    }
		    
		    if (puitsActuel.getTas().elementExists(abscisse1, ordonnee1)) {
		        throw new BloxException("Collision détectée avec un élément du tas", BloxException.BLOX_COLLISION);
		    }
		}
		
		public void deplacerDe(int deltaX, int deltaY) {
		        if (deltaX < -1 || deltaX > 1 || deltaY < 0 || deltaY > 1) {
		            throw new IllegalArgumentException("Déplacement invalide");
		        }

		        for (Element element : elements) {
		            element.deplacerDe(deltaX, deltaY);  
		        }
		}
		public void tourner(boolean sensHoraire){
		         
		            Coordonnees reference = elements[0].getCoordonnees(); 
		            int dx = -reference.getAbscisse();
		            int dy = -reference.getOrdonnee();
		            for (Element element : elements) {
		                element.deplacerDe(dx, dy);
		            }

		          
		            for (int i = 1; i < elements.length; i++) {
		                Coordonnees coord = elements[i].getCoordonnees();
		                int x = coord.getAbscisse();
		                int y = coord.getOrdonnee();
		                if (sensHoraire) {
		                   
		                    elements[i].setCoordonnees(new Coordonnees(-y, x));
		                } else {
		                    elements[i].setCoordonnees(new Coordonnees(y, -x));
		                }
		            }

		           for (Element element : elements) {
		                element.deplacerDe(-dx, -dy);
		            }
		        }
		    }
		 

