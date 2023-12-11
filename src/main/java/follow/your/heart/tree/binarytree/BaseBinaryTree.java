package follow.your.heart.tree.binarytree;

public class BaseBinaryTree implements BinaryTree{
    Node root;

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        if (root == null) return "";
        StringBuilder builder = new StringBuilder();
        appendNodeToStringRecursive(root, builder);
        return builder.toString();
    }

    private void appendNodeToStringRecursive(Node node, StringBuilder builder) {
        appendNodeToString(node, builder);
        if (node.left != null) {
            builder.append(" L{");
            appendNodeToStringRecursive(node.left, builder);
            builder.append("}");
        }
        if (node.right != null) {
            builder.append(" R{");
            appendNodeToStringRecursive(node.right, builder);
            builder.append("}");
        }
    }

    private void appendNodeToString(Node node, StringBuilder builder) {
        builder.append(node.data);
    }
}
