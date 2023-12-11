package follow.your.heart.tree.binarytree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstTraversalRecursiveTest {
    BinarySearchTree bst = new BinarySearchTreeRecursive();
    DepthFirstTraversal depthFirstTraversal;
    @BeforeEach
    void init() {
        bst.insertNode(10);
        bst.insertNode(20);
        bst.insertNode(50);
        bst.insertNode(30);
        bst.insertNode(33);
        bst.insertNode(32);
        bst.insertNode(29);
        bst.insertNode(27);
        bst.insertNode(40);
        bst.insertNode(4);
        bst.insertNode(7);
        depthFirstTraversal = new DepthFirstTraversalRecursive((BinaryTree) bst);
        System.out.println(bst);
    }

    @Test
    void traversePreOrder() {
        depthFirstTraversal.traversePreOrder(node -> System.out.println(node.data));
    }

    @Test
    void traversePostOrder() {
        depthFirstTraversal.traversePostOrder(node -> System.out.println(node.data));
    }

    @Test
    void traverseReverseInOrder() {
        depthFirstTraversal.traverseReverseInOrder(node -> System.out.println(node.data));
    }

    @Test
    void traverseInOrder() {
        depthFirstTraversal.traverseInOrder(node -> System.out.println(node.data));
    }
}