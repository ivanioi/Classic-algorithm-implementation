package follow.your.heart.tree.binarytree;

public class BinarySearchTreeRecursive extends BaseBinaryTree implements BinarySearchTree{
    @Override
    public Node searchNode(int key) {
        return searchNode(key, root);
    }

    private Node searchNode(int key, Node node) {
        if (node == null) {
            return null;
        }
        if (key == node.data) {
            return node;
        } else if (key < node.data) {
            return searchNode(key, node.left);
        } else {
            return searchNode(key, node.right);
        }
    }


    @Override
    public void insertNode(int key) {
        root = inseartNode(key, root);
    }
    private Node inseartNode(int key, Node node) {
        if (node == null) {
            node = new Node(key);
        } else if (key < node.data) {
            node.left = inseartNode(key, node.left);
            node.left.parent = node;
        } else if (key > node.data) {
            node.right = inseartNode(key, node.right);
            node.right.parent = node;
        } else throw new IllegalArgumentException("BST already contains a node with key " + key);
        return node;
    }

    @Override
    public void deleteNode(int key) {
        Node node = searchNode(key);
        if (node == null) return;
        deleteNode(root, node);
    }

    /**
     * 该删除，在被删结点具有两个子结点时，并没有真正的删除目标结点，而是通过删除 successor 和修改目标结点 data 实现的。
     * @param root
     * @param node
     */
    private void deleteNode(Node root, Node node) {
        if (node.left == null) {
            shiftNodes(root, node, node.right);
        } else if (node.right == null) {
            shiftNodes(root, node, node.left);
        } else {
            Node successor = findMinimum(node.right);
//            if (successor != node.right) {
//                shiftNodes(root, successor, successor.right);
//                successor.right = node.right;
//                successor.right.parent = successor;
//                successor.left = node.left;
//                successor.left.parent = successor;
//                successor.parent = node.parent;
//            } else {
//                shiftNodes(root, node, successor);
//                successor.left = node.left;
//                successor.left.parent = successor;
//                successor.parent = node.parent;
//            }
            // 该步骤等同于以上注释，通过修改数据来实现替换结点！
            shiftNodes(root, successor, successor.right);
            node.data = successor.data;
        }
    }

    private Node findMinimum(Node node) {
        if (node.left != null) {
            return node.left;
        }
        return node;
    }



    /**
     * 使用 v 节点代替 u 节点
     * @param root
     * @param u
     * @param v
     */
    private void shiftNodes(Node root, Node u, Node v) {
        if (u.parent == null ) {
            root = v;
        } else if (u.parent.left == u) {
            u.parent.left = v;
        } else if (u.parent.right == u) {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

}
