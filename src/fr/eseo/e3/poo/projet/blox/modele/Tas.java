package fr.eseo.e3.poo.projet.blox.modele;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Tas {
	
	    private Puits puits;
	    private List<Element> elements;

	    public Tas(Puits puits) {
	        this.puits = puits;
	        this.elements = new ArrayList<>();
	    }

	    public Tas(Puits puits, int nbElements, int nbLignes) {
	        this.puits = puits;
	        this.elements = new ArrayList<>();
	        Random random = new Random();
	        construireTas(nbElements, nbLignes, random);
	    }

	    public Tas(Puits puits, int nbElements, int nbLignes, Random random) {
	        this.puits = puits;
	        this.elements = new ArrayList<>();
	        construireTas(nbElements, nbLignes, random);
	    }

	    public Tas(Puits puits, int nbElements) {
	        this.puits = puits;
	        this.elements = new ArrayList<>();
	        int nbLignes = (nbElements / puits.getLargeur()) + 1;
	        Random random = new Random();
	        construireTas(nbElements, nbLignes, random);
	    }

	    public Puits getPuits() {
	        return puits;
	    }

	    public List<Element> getElements() {
	        return elements;
	    }

	    private void construireTas(int nbElements, int nbLignes, Random rand){
			if (nbElements != 0 && nbElements >= getPuits().getLargeur() * nbLignes) {
	            throw new IllegalArgumentException("impossible");
	        } 
			else {
	            int nbElementsEndroit = 0;
				while (nbElementsEndroit < nbElements) {
	                int y= this.getPuits().getProfondeur() - (rand.nextInt(nbLignes) + 1);
	                int x= rand.nextInt(this.getPuits().getLargeur());
	                if (!elementExists(x, y)) {
	                    int numCouleur = rand.nextInt(Couleur.values().length);
	                    getElements().add(new Element(x, y, Couleur.values()[numCouleur]));
	                    nbElementsEndroit++;
	                }

				}

			}
		}

	    public boolean elementExists(int abscisse, int ordonnee) {
	    	    for (Element element : getElements()) {
	    	        int x = element.getCoordonnees().getAbscisse();
	    	        int y = element.getCoordonnees().getOrdonnee();
	    	        if (x == abscisse && y == ordonnee) {
	    	            return true;
	    	        }
	    	    }
	    	    return false;
	    	}
	   
	}


