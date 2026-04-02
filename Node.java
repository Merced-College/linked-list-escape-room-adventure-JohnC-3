/**
 * Represents a single node in the linked list structure for scenes.
 * Each node contains scene data and a reference to the next node.
 */
public class Node {
    // The scene data stored in this node
    private Scene data;
    // Reference to the next node in the linked list
    private Node next;

    /**
     * Constructor for creating a new node with scene data.
     *
     * @param data the Scene object to store in this node
     */
    public Node(Scene data) {
        this.data = data;
        this.next = null; // Initially, this node doesn't point to anything
    }

    /**
     * Gets the scene data stored in this node.
     *
     * @return the Scene object
     */
    public Scene getData() {
        return data;
    }

    /**
     * Gets the reference to the next node in the linked list.
     *
     * @return the next Node, or null if this is the last node
     */
    public Node getNext() {
        return next;
    }

    /**
     * Sets the reference to the next node in the linked list.
     *
     * @param next the Node to link to
     */
    public void setNext(Node next) {
        this.next = next;
    }
}
