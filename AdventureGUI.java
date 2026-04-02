import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * GUI version of the Escape Room Adventure game.
 * Provides a graphical interface using Swing components for a better user experience.
 * Displays scenes with their descriptions, available choices as buttons, and the player's inventory.
 */
public class AdventureGUI extends JFrame {

    // The underlying game engine
    private final AdventureGame game;
    // The scene currently being displayed
    private Scene currentScene;
    // The player object from the game
    private final Player player;

    // GUI components for displaying scene information
    private final JLabel titleLabel;           // Displays the scene title
    private final JTextArea descriptionArea;   // Displays the scene description
    private final JPanel choicesPanel;         // Container for choice buttons
    private final JTextArea inventoryArea;     // Displays the player's inventory
    private final JButton takeItemButton;      // Button to take item from current scene
    private final JLabel statusLabel;          // Displays status messages

    /**
     * Constructor - initializes the GUI and sets up all components.
     */
    public AdventureGUI() {
        // Initialize the game engine
        game = new AdventureGame();
        currentScene = game.getCurrentScene();
        player = game.getPlayer();

        // Set up the main window
        setTitle("Escape Room Adventure - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);

        // Main layout uses BorderLayout with spacing
        setLayout(new BorderLayout(10, 10));

        // Top: Title label
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Center area: Description on top, Choices below it
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        
        // Description text area
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        centerPanel.add(descScroll, BorderLayout.CENTER);

        // Choices panel for displaying choice buttons
        choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        choicesPanel.setBorder(BorderFactory.createTitledBorder("Your Choices"));
        choicesPanel.setPreferredSize(new Dimension(500, 220));   // Set minimum size for visibility
        centerPanel.add(choicesPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Right sidebar: Inventory display
        JPanel invPanel = new JPanel(new BorderLayout());
        invPanel.setBorder(BorderFactory.createTitledBorder("Your Inventory"));
        inventoryArea = new JTextArea(15, 25);
        inventoryArea.setEditable(false);
        inventoryArea.setFont(new Font("Arial", Font.PLAIN, 14));
        invPanel.add(new JScrollPane(inventoryArea), BorderLayout.CENTER);
        add(invPanel, BorderLayout.EAST);

        // Bottom: Take item button and status label
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        takeItemButton = new JButton("Take Item");
        takeItemButton.setVisible(false);
        takeItemButton.setFont(new Font("Arial", Font.PLAIN, 14));
        takeItemButton.addActionListener(e -> takeCurrentItem());

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        bottomPanel.add(takeItemButton);
        bottomPanel.add(statusLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Display the first scene
        updateDisplay();

        // Let Swing calculate optimal size based on content
        pack();
        setVisible(true);
    }

    /**
     * Updates the GUI display with the current scene's information.
     * Refreshes the title, description, choices, inventory, and item button.
     */
    private void updateDisplay() {
        if (currentScene == null) return;

        // Update the scene title
        titleLabel.setText(currentScene.getTitle());
        // Update the scene description
        descriptionArea.setText(currentScene.getDescription());

        // Clear all previous choice buttons
        choicesPanel.removeAll();

        // Add a button for each available choice
        ArrayList<Choice> choices = currentScene.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            Choice c = choices.get(i);
            JButton btn = new JButton((i + 1) + ". " + c.getText());
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);   // Aligns buttons to the left
            final int index = i;
            // Add action listener to handle choice selection
            btn.addActionListener(e -> makeChoice(index));
            choicesPanel.add(btn);
            choicesPanel.add(Box.createRigidArea(new Dimension(0, 8))); // Add spacing between buttons
        }

        // Update the inventory display
        inventoryArea.setText(player.getInventoryText());

        // Show take item button if an item is available in this scene
        if (currentScene.getItem() != null) {
            takeItemButton.setText("Take: " + currentScene.getItem().getName());
            takeItemButton.setVisible(true);
        } else {
            takeItemButton.setVisible(false);
        }

        // Refresh the GUI components
        choicesPanel.revalidate();
        choicesPanel.repaint();
        revalidate();
        repaint();
        pack();                    

        // Check if this is the final room (scene 5) where win/lose is determined
        if (currentScene.getSceneId() == 5) {
            handleFinalRoom();
        }
    }

    /**
     * Handles when the player selects a choice.
     * Transitions to the next scene and updates the display.
     *
     * @param index the index of the selected choice
     */
    private void makeChoice(int index) {
        ArrayList<Choice> choices = currentScene.getChoices();
        // Validate the choice index
        if (index >= 0 && index < choices.size()) {
            // Get the ID of the next scene from the selected choice
            int nextId = choices.get(index).getNextSceneId();
            // Find and transition to the next scene
            currentScene = game.getSceneList().findSceneById(nextId);
            game.setCurrentScene(currentScene);
            // Refresh the GUI with the new scene
            updateDisplay();
        }
    }

    /**
     * Handles taking the item from the current scene and adding it to inventory.
     */
    private void takeCurrentItem() {
        Item item = currentScene.getItem();
        if (item != null) {
            // Add the item to the player's inventory
            player.addItem(item);
            // Remove the item from the scene
            currentScene.removeItem();
            // Show a status message
            statusLabel.setText(item.getName() + " added to inventory!");
            // Refresh the GUI
            updateDisplay();
        }
    }

    /**
     * Handles the final room logic - checks if the player has won or lost.
     * Displays a dialog with the result and disables further choices.
     */
    private void handleFinalRoom() {
        // Check if the player has both required items to win
        boolean hasKeycard = player.hasItem("Keycard");
        boolean hasCodeNote = player.hasItem("Code Note");

        String message;
        // Determine win/loss message based on required items
        if (hasKeycard && hasCodeNote) {
            message = "🎉 You used the Keycard and Code Note to unlock the exit!\nYou escaped! You win!";
        } else {
            message = "The exit will not open.\nYou are missing required items.\nYou need: Keycard and Code Note.";
        }

        // Show the result in a dialog box
        JOptionPane.showMessageDialog(this, message, "Final Room", JOptionPane.INFORMATION_MESSAGE);

        // Disable further choices after game ends
        choicesPanel.setEnabled(false);
    }

    /**
     * Main method - entry point for the GUI application.
     * Launches the GUI on the Event Dispatch Thread.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdventureGUI().setVisible(true);
        });
    }
}