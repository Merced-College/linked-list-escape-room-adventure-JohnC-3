import java.util.ArrayList;

/**
 * Represents the player in the escape room adventure.
 * Manages the player's inventory of collected items.
 */
public class Player {
    // Collection of items that the player has picked up
    private ArrayList<Item> inventory;

    /**
     * Constructor - initializes an empty inventory.
     */
    public Player() {
        inventory = new ArrayList<>();
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item the Item to add
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Gets the list of items in the player's inventory.
     *
     * @return ArrayList of Item objects
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Checks if the player has a specific item in their inventory.
     *
     * @param itemName the name of the item to search for (case-insensitive)
     * @return true if the item is in inventory, false otherwise
     */
    public boolean hasItem(String itemName) {
        // Loop through inventory and check each item's name
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a formatted text representation of the player's inventory.
     * Displays "Inventory: empty" if no items are held,
     * or lists all items with bullet points.
     *
     * @return a formatted string showing inventory contents
     */
    public String getInventoryText() {
        if (inventory.isEmpty()) {
            return "Inventory: empty";
        }

        String result = "Inventory:\n";
        for (Item item : inventory) {
            result += "- " + item.getName() + "\n";
        }
        return result;
    }
}
