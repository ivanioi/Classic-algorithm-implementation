package follow.your.heart.tree.binarytree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    static final boolean BLACK = true;
    static final boolean RED = false;
    static Node AddLeft (Node node, Node subNode) {
        node.left = subNode;
        subNode.parent = node;
        return subNode;
    }
    static Node AddRigth (Node node, Node subNode) {
        node.right = subNode;
        subNode.parent = node;
        return subNode;
    }

    @Test
    void insertNode() {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insertNode(17);
        redBlackTree.insertNode(9);
        redBlackTree.insertNode(19);
        redBlackTree.insertNode(18);
        redBlackTree.insertNode(75);
        redBlackTree.insertNode(73);
        redBlackTree.insertNode(81);
        redBlackTree.insertNode(60);
        redBlackTree.insertNode(70);
        System.out.println(redBlackTree);
    }

    @Test
    void deleteNode() {
        System.out.println("--------- CASE 1");
        RedBlackTree tree = new RedBlackTree();
        tree.insertNode(17);
        tree.insertNode(19);
        System.out.println(tree);
        tree.deleteNode(17);
        System.out.println(tree);
        tree.deleteNode(19);
        System.out.println(tree);

        System.out.println("--------- CASE 2");
        tree = new RedBlackTree();
        tree.root = new Node(17, BLACK);
        AddLeft(tree.root, new Node(9, BLACK));
        Node n1 = AddRigth(tree.root, new Node(19, RED));
        AddRigth(n1, new Node(75, BLACK));
        AddLeft(n1, new Node(18, BLACK));
        System.out.println(tree);
        tree.deleteNode(9);
        System.out.println(tree);

        System.out.println("--------- CASE 3");
        tree = new RedBlackTree();
        tree.root = new Node(17, BLACK);
        Node n2 = AddLeft(tree.root, new Node(9, BLACK));
        AddLeft(n2, new Node(3, RED));
        AddRigth(n2, new Node(12, RED));
        n1 = AddRigth(tree.root, new Node(19, RED));
        AddRigth(n1, new Node(75, BLACK));
        AddLeft(n1, new Node(18, BLACK));
        System.out.println(tree);
        tree.deleteNode(75);
        System.out.println(tree);

        System.out.println("--------- CASE 4");
        tree = new RedBlackTree();
        tree.root = new Node(17, RED);
        n2 = AddLeft(tree.root, new Node(9, BLACK));
        AddLeft(n2, new Node(3, BLACK));
        AddRigth(n2, new Node(12, BLACK));
        n1 = AddRigth(tree.root, new Node(19, BLACK));
        AddRigth(n1, new Node(75, BLACK));
        AddLeft(n1, new Node(18, BLACK));
        System.out.println(tree);
        tree.deleteNode(18);
        System.out.println(tree);

        System.out.println("--------- CASE 5");
        tree = new RedBlackTree();
        tree.root = new Node(17, BLACK);
        n2 = AddLeft(tree.root, new Node(9, BLACK));
        n1 = AddRigth(tree.root, new Node(19, RED));
        Node n3 = AddRigth(n1, new Node(75, BLACK));
        AddLeft(n1, new Node(18, BLACK));
        AddLeft(n3, new Node(24, RED));
        System.out.println(tree);
        tree.deleteNode(18);
        System.out.println(tree);

        System.out.println("--------- CASE 6");
        tree = new RedBlackTree();
        tree.root = new Node(17, BLACK);
        n2 = AddLeft(tree.root, new Node(9, BLACK));
        n1 = AddRigth(tree.root, new Node(19, RED));
        n3 = AddRigth(n1, new Node(75, BLACK));
        AddLeft(n1, new Node(18, BLACK));
        AddLeft(n3, new Node(24, RED));
        AddRigth(n3, new Node(81, RED));
        System.out.println(tree);
        tree.deleteNode(18);
        System.out.println(tree);

    }

}