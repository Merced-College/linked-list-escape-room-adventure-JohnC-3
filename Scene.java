import java.util.ArrayList;

/**
 * Represents a single scene/room in the escape room adventure.
 * Each scene has a description, available choices, and potentially an item to collect.
 */
public class Scene {
    // Unique identifier for this scene
    private int sceneId;
    // The title/name of this scene
    private String title;
    // The detailed description of what the player sees
    private String description;
    // Path to the image file associated with this scene
    private String imagePath;
    // List of choices available to the player in this scene
    private ArrayList<Choice> choices;
    // The item (if any) that can be picked up in this scene
    private Item item;

    /**
     * Constructor for creating a new scene.
     *
     * @param sceneId unique ID for this scene
     * @param title the name of the scene
     * @param description detailed description of the scene
     * @param imagePath path to the image file for this scene
     * @param item the Item available in this scene (null if no item)
     */
    public Scene(int sceneId, String title, String description, String imagePath, Item item) {
        this.sceneId = sceneId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.item = item;
        this.choices = new ArrayList<>();
    }

    /**
     * Adds a choice option to this scene.
     *
     * @param choice the Choice to add
     */
    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    /**
     * Gets the unique ID of this scene.
     *
     * @return the scene ID
     */
    public int getSceneId() {
        return sceneId;
    }

    /**
     * Gets the title of this scene.
     *
     * @return the scene title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of this scene.
     *
     * @return the scene description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the path to the image file for this scene.
     *
     * @return the image file path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the list of choices available in this scene.
     *
     * @return ArrayList of Choice objects
     */
    public ArrayList<Choice> getChoices() {
        return choices;
    }

    /**
     * Gets the item available in this scene.
     *
     * @return the Item, or null if no item is available
     */
    public Item getItem() {
        return item;
    }

    /**
     * Removes the item from this scene (typically after player picks it up).
     */
    public void removeItem() {
        item = null;
    }

    /**
     * Returns a string representation of this scene.
     *
     * @return formatted string like "Scene 1: Room Title"
     */
    @Override
    public String toString() {
        return "Scene " + sceneId + ": " + title;
    }
}
