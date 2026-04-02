import java.util.Scanner;

/**
 * Core game logic for the Escape Room Adventure.
 * Manages game flow, scene transitions, item collection, and win conditions.
 * This is the console-based version of the game.
 */
public class AdventureGame {
    // Linked list of all scenes in the game
    private SceneLinkedList scenes;
    // The player object that tracks inventory
    private Player player;
    // The scene currently being displayed
    private Scene currentScene;
    // Scanner for reading user input from console
    private Scanner scanner;

    /**
     * Constructor - initializes the game by loading scenes and setting up the player.
     */
    public AdventureGame() {
        // Load all scenes from the CSV file
        scenes = GameLoader.loadScenes("data/scenes.csv");
        // Create a new player
        player = new Player();
        // Start at scene 1
        currentScene = scenes.findSceneById(1);
        // Set up scanner for console input
        scanner = new Scanner(System.in);
    }

    /**
     * Gets the current scene the player is in.
     *
     * @return the current Scene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Sets the current scene (used for transitioning between scenes).
     *
     * @param scene the Scene to set as current
     */
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    /**
     * Gets the player object.
     *
     * @return the Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the linked list of all scenes.
     *
     * @return the SceneLinkedList
     */
    public SceneLinkedList getSceneList() {
        return scenes;
    }

    /**
     * Main game loop - displays scenes, handles user choices, and manages the game flow.
     */
    public void play() {
        System.out.println("Welcome to Escape Room Adventure!");
        System.out.println("Collect the correct items before reaching the exit.\n");

        // Continue playing until currentScene is null or the game ends
        while (currentScene != null) {
            displayCurrentScene();
            // Check if we've reached the final room (win/lose condition)
            if (currentScene.getSceneId() == 5) {
                handleFinalRoom();
                break;
            }
            // Offer to pick up items if one is available in this scene
            if (currentScene.getItem() != null) {
                handleItemPickup();
            }
            // Display choices and get player input
            handleChoices();
        }
        scanner.close();
    }

    /**
     * Displays the current scene's title, description, and player's inventory.
     */
    private void displayCurrentScene() {
        System.out.println("\n==============================");
        System.out.println(currentScene.getTitle());
        System.out.println("==============================");
        System.out.println(currentScene.getDescription());
        System.out.println();
        System.out.println(player.getInventoryText());
        System.out.println();
    }

    /**
     * Handles the item pickup prompt if an item is available in the current scene.
     */
    private void handleItemPickup() {
        Item item = currentScene.getItem();
        System.out.println("You found an item: " + item.getName());
        System.out.print("Would you like to pick it up? (yes/no): ");
        String answer = scanner.nextLine();
        // If player answers yes, add item to inventory and remove it from the scene
        if (answer.equalsIgnoreCase("yes")) {
            player.addItem(item);
            currentScene.removeItem();
            System.out.println(item.getName() + " added to your inventory.");
        }
    }

    /**
     * Displays available choices and handles the player's selection.
     */
    private void handleChoices() {
        System.out.println("Choose an option:");
        // Display all available choices
        for (int i = 0; i < currentScene.getChoices().size(); i++) {
            System.out.println((i + 1) + ". " + currentScene.getChoices().get(i).getText());
        }
        System.out.print("Enter choice number: ");
        int userChoice = Integer.parseInt(scanner.nextLine());
        // Validate the choice and transition to the next scene
        if (userChoice >= 1 && userChoice <= currentScene.getChoices().size()) {
            int nextId = currentScene.getChoices().get(userChoice - 1).getNextSceneId();
            currentScene = scenes.findSceneById(nextId);
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    /**
     * Handles the final room logic - checks if player has winning items.
     * To win, the player must have both the "Keycard" and "Code Note".
     */
    private void handleFinalRoom() {
        System.out.println("You reached the exit door.");
        // Check if the player has both required items
        boolean hasKeycard = player.hasItem("Keycard");
        boolean hasCodeNote = player.hasItem("Code Note");
        if (hasKeycard && hasCodeNote) {
            // Player has all required items - game won!
            System.out.println("You used the Keycard and the Code Note to unlock the exit.");
            System.out.println("You escaped. You win!");
        } else {
            // Player is missing required items - game lost!
            System.out.println("The exit will not open.");
            System.out.println("You are missing the required items.");
            System.out.println("To win, you need: Keycard and Code Note.");
        }
    }
}