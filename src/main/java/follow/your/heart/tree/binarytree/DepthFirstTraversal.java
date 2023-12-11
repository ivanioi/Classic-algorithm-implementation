package follow.your.heart.tree.binarytree;

public interface DepthFirstTraversal {
    void traversePreOrder(NodeVisitor visitor);
    void traversePostOrder(NodeVisitor visitor);
    void traverseInOrder(NodeVisitor visitor);
    void traverseReverseInOrder(NodeVisitor visitor);
}
