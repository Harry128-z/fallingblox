package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class TasTest {

    public static void main(String[] args) {
        testConstructionTas();
    }

    static void testConstructionTas() {
        Puits puits = new Puits(10, 20);
        
        Tas tas = new Tas(puits, 30, 5, new Random());
       
        int nbElementsAttendus = 30;
        int nbElementsDansTas = tas.getElements().size();
        if (nbElementsDansTas != nbElementsAttendus) {
            System.out.println("Erreur : nombre d'éléments dans le tas incorrect.");
        } else {
            System.out.println("Test réussi : nombre d'éléments dans le tas correct.");
        }
    }
}