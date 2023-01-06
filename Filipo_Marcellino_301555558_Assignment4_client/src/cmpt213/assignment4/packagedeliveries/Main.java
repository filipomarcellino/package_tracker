package cmpt213.assignment4.packagedeliveries;

import cmpt213.assignment4.packagedeliveries.view.GUI;

/**
 * Main class to run the program
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
