package follow.your.heart.tree.binarytree;

public class DepthFirstTraversalRecursive implements DepthFirstTraversal{
    private final BinaryTree tree;

    public DepthFirstTraversalRecursive(BinaryTree tree) {
        this.tree = tree;
    }

    @Override
    public void traversePreOrder(NodeVisitor visitor) {
        traversePreOrder(tree.getRoot(), visitor);
    }

    public static void traversePreOrder(Node node, NodeVisitor visitor) {
        if (node == null) {
            return;
        }
        visitor.visit(node);
        traversePreOrder(node.left, visitor);
        traversePreOrder(node.right, visitor);
    }

    @Override
    public void traversePostOrder(NodeVisitor visitor) {
        traversePostOrder(tree.getRoot(), visitor);
    }
    public static void traversePostOrder(Node node, NodeVisitor visitor) {
        if (node == null) {
            return;
        }
        traversePostOrder(node.left, visitor);
        traversePostOrder(node.right, visitor);
        visitor.visit(node);
    }

    @Override
    public void traverseInOrder(NodeVisitor visitor) {
        traverseInOrder(tree.getRoot(), visitor);
    }
    public static void traverseInOrder(Node node, NodeVisitor visitor) {
        if (node == null) {
            return;
        }
        traverseInOrder(node.left, visitor);
        visitor.visit(node);
        traverseInOrder(node.right, visitor);
    }

    @Override
    public void traverseReverseInOrder(NodeVisitor visitor) {
        traverseReverseInOrder(tree.getRoot(), visitor);
    }

    public static void traverseReverseInOrder(Node node, NodeVisitor visitor) {
        if (node == null) {
            return;
        }
        traverseReverseInOrder(node.right, visitor);
        visitor.visit(node);
        traverseReverseInOrder(node.left, visitor);
    }
}
