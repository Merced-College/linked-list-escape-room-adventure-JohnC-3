/**
 * Represents an item that can be found and collected in the game.
 * Items have a name and description.
 */
public class Item {
    // The name of the item (e.g., "Keycard")
    private String name;
    // A description of what the item is or what it does
    private String description;

    /**
     * Constructor for creating a new item.
     *
     * @param name the name of the item
     * @param description a description of the item
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name of this item.
     *
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of this item.
     *
     * @return the item description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of this item in the format: "name - description".
     *
     * @return a formatted string containing name and description
     */
    @Override
    public String toString() {
        return name + " - " + description;
    }
}
