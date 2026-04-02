/**
 * Represents a choice that a player can make at a scene.
 * Each choice has descriptive text and leads to the next scene.
 */
public class Choice {
    // The text that describes this choice option to the player
    private String text;
    // The ID of the scene that this choice leads to
    private int nextSceneId;

    /**
     * Constructor for creating a new choice.
     *
     * @param text the description of this choice
     * @param nextSceneId the ID of the scene to transition to if this choice is selected
     */
    public Choice(String text, int nextSceneId) {
        this.text = text;
        this.nextSceneId = nextSceneId;
    }

    /**
     * Gets the text description of this choice.
     *
     * @return the choice text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the ID of the next scene this choice leads to.
     *
     * @return the next scene ID
     */
    public int getNextSceneId() {
        return nextSceneId;
    }

    /**
     * Returns a string representation of this choice (the text).
     *
     * @return the choice text
     */
    @Override
    public String toString() {
        return text;
    }
}
