import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class for loading game data from a CSV file.
 * Parses scene information and creates a linked list of all scenes.
 */
public class GameLoader {

    /**
     * Loads all scenes from a CSV file and returns them as a linked list.
     * Expected CSV format: sceneId,title,description,imagePath,itemName,itemDescription,choice1Text,choice1NextId,choice2Text,choice2NextId
     *
     * @param fileName the path to the CSV file containing scene data
     * @return a SceneLinkedList containing all loaded scenes
     */
    public static SceneLinkedList loadScenes(String fileName) {
        // Create a linked list to store all scenes
        SceneLinkedList scenes = new SceneLinkedList();

        try {
            // Open the file for reading
            Scanner fileScanner = new Scanner(new File(fileName));

            // Skip the header line if it exists
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // skip header
            }

            // Read each line (scene) from the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Split the line by commas to extract individual fields
                String[] parts = line.split(",");

                // Parse scene attributes from the CSV data
                int sceneId = Integer.parseInt(parts[0]);
                String title = parts[1];
                String description = parts[2];
                String imagePath = parts[3];
                String itemName = parts[4];
                String itemDescription = parts[5];

                // Create an item only if one is specified (not "none")
                Item item = null;
                if (!itemName.equalsIgnoreCase("none")) {
                    item = new Item(itemName, itemDescription);
                }

                // Create the scene with the parsed attributes
                Scene scene = new Scene(sceneId, title, description, imagePath, item);

                // Parse choice data from the CSV
                String choice1Text = parts[6];
                int choice1NextId = Integer.parseInt(parts[7]);
                String choice2Text = parts[8];
                int choice2NextId = Integer.parseInt(parts[9]);

                // Add both choices to the scene
                scene.addChoice(new Choice(choice1Text, choice1NextId));
                scene.addChoice(new Choice(choice2Text, choice2NextId));

                // Add the complete scene to the linked list
                scenes.add(scene);
            }

            // Close the file scanner
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + fileName);
        }

        return scenes;
    }
}
