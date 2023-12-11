package follow.your.heart.tree.binarytree;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeRecursiveTest {

    @org.junit.jupiter.api.Test
    void searchNode() {
        BinarySearchTree bst = new BinarySearchTreeRecursive();
        bst.insertNode(10);
        bst.insertNode(20);
        bst.insertNode(50);
        bst.insertNode(30);
        bst.insertNode(4);
        bst.insertNode(7);
        assertEquals(null, bst.searchNode(23));
        assertEquals(20, bst.searchNode(20).data);
        System.out.println(bst);
    }

    @org.junit.jupiter.api.Test
    void insertNode() {
        BinarySearchTree bst = new BinarySearchTreeRecursive();
        bst.insertNode(10);
        bst.insertNode(20);
        bst.insertNode(50);
        bst.insertNode(30);
        bst.insertNode(4);
        bst.insertNode(7);
        assertEquals(true, BinarySearchTreeValidator.isBstWithoutDuplicates((BinaryTree) bst));
        System.out.println(bst);
    }

    @org.junit.jupiter.api.Test
    void deleteNode() {
        BinarySearchTree bst = new BinarySearchTreeRecursive();
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

        bst.deleteNode(50);
        assertEquals(null, bst.searchNode(50));
        assertEquals(true, BinarySearchTreeValidator.isBstWithoutDuplicates((BinaryTree) bst));
        System.out.println(bst);
    }
}