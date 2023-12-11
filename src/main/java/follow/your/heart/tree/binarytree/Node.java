package follow.your.heart.tree.binarytree;

public class Node {
    int data;
    Node left;
    Node right;
    Node parent;
    // false: red
    boolean color;
    public Node(int data) {
        this.data = data;
    }

    public Node(int data, Boolean color) {
        this.color = color;
        this.data = data;
    }

    public int getData() {
        return data;
    }
}
