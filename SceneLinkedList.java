/**
 * A custom linked list data structure for storing Scene objects.
 * Implements a singly linked list where each node contains a Scene.
 */
public class SceneLinkedList {
    // Reference to the first node in the linked list
    private Node head;
    // The number of scenes/nodes in the linked list
    private int size;

    /**
     * Constructor - initializes an empty linked list.
     */
    public SceneLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Adds a new scene to the end of the linked list.
     *
     * @param scene the Scene to add
     */
    public void add(Scene scene) {
        // Create a new node containing the scene
        Node newNode = new Node(scene);

        // If the list is empty, make this the head
        if (head == null) {
            head = newNode;
        } else {
            // Otherwise, traverse to the end of the list and append
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }

        size++;
    }

    /**
     * Searches for and returns a scene with the specified ID.
     *
     * @param sceneId the ID of the scene to find
     * @return the Scene with the matching ID, or null if not found
     */
    public Scene findSceneById(int sceneId) {
        // Start at the beginning of the list
        Node current = head;

        // Traverse the list until we find the matching scene
        while (current != null) {
            if (current.getData().getSceneId() == sceneId) {
                return current.getData();
            }
            current = current.getNext();
        }

        // Return null if the scene was not found
        return null;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return true if the list contains no nodes, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the number of scenes in the linked list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Displays all scenes in the linked list to the console.
     */
    public void displayScenes() {
        // Traverse the list and print each scene
        Node current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}