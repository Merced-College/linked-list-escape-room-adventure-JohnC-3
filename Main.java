/**
 * Main entry point for the Escape Room Adventure application.
 * Initializes and launches the GUI on the Event Dispatch Thread.
 */
public class Main {
    /**
     * Main method - starts the application by creating and displaying the AdventureGUI.
     * Uses SwingUtilities.invokeLater to ensure GUI components are created on the Event Dispatch Thread.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new AdventureGUI().setVisible(true);
        });
    }
}