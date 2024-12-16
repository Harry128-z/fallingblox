package fr.eseo.e3.poo.projet.blox.controleur;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

// anti-plagiat chatGPT
public class PieceRotationTest {
    public static void main(String[] args) {
        Puits puits = new Puits(10, 20);
        VuePuits vuePuits = new VuePuits(puits);
        PieceRotation pieceRotation = new PieceRotation(vuePuits);
        JFrame frame = new JFrame("Test de rotation de pièce");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(vuePuits);
        frame.pack();
        frame.setVisible(true);
        MouseEvent rightClick = new MouseEvent(vuePuits, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
                0, 50, 50, 1, false, MouseEvent.BUTTON3);
        pieceRotation.mouseClicked(rightClick);
        
        MouseEvent leftClick = new MouseEvent(vuePuits, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
                0, 50, 50, 1, false, MouseEvent.BUTTON1);
        pieceRotation.mouseClicked(leftClick);
    }
}
