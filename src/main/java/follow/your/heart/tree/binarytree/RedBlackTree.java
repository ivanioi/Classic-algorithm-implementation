package follow.your.heart.tree.binarytree;

import com.sun.org.apache.bcel.internal.generic.BALOAD;

public class RedBlackTree extends BaseBinaryTree implements BinarySearchTree{
    static final boolean RED = false;
    static final boolean BLACK = true;

    // 算法实现需要其实例，临时替补那些无子结点的被删除结点后的空位置(即 move-up-node 为 null时，用 NilNode 表示该 move-up-node).
    private static class NilNode extends Node {
        private NilNode() {
            super(0);
            this.color = BLACK;
        }
    }

    @Override
    public Node searchNode(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.data) {
                return node;
            } else if (key < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    @Override
    public void insertNode(int key) {
        Node node = root;
        Node parent = null;

        // Traverse the tree to the left or right depending on the key
        while (node != null) {
            parent = node;
            if (key < node.data) {
                node = node.left;
            } else if (key > node.data) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with key " + key);
            }
        }

        // Insert new node
        Node newNode = new Node(key);
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
        } else if (key < parent.data) {
            parent.left = newNode;
        } else if (key > parent.data) {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
    }

    /**
     * 一共 5 种状况需要修复
     * @param node
     */
    private void fixRedBlackPropertiesAfterInsert(Node node){
        Node parent = node.parent;

        // Case 1: 父结点为空，我们已经到达 root, 结束递归.
        if (parent == null) {
            // 不建议下行代码，迫使 root 始终为黑色.
            // node.color = BLACK;
            return;
        }

        // 父结点为黑色，不违反规则结束递归.
        if (parent.color == BLACK) {
            return;
        }

        // 以下是父结点为红色时，来修复违规
        Node grandparent = parent.parent;

        // Case 2: root 结点为 RED
        // 如果我们强制 root 结点必须为 BLACK，那么以下 if 语句可以省略.
        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        // 获取叔结点(可能为 null，其表示 BLACK)
        Node uncle = getUncle(parent);

        // Case 3: 叔父结点都是红色，对 parent, uncle, grandparent 结点进行重新上色
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;

            // 需要对 grandparent 结点进行递归修复
            // 因为此时 grandparent 结点颜色可能违规，它可能有一个 RED 颜色的父结点.
            fixRedBlackPropertiesAfterInsert(grandparent);
        }
        else if (parent == grandparent.left) {
            // Case 4a: Uncle 结点是黑色，并且插入结点是 left-inner-child(意味着其与 parent, grandparent 结点相连接后，可以在左侧形成一个三角形)
            if (node == parent.right) {
                rotateLeft(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                // 为了将代码复用到 Case 5a，这里需要设置以下 parent 代指以下 Case 4a 经过两次旋转后的 grandparent 结点(当其只旋转了一次).
                parent = node;
            }

            // Case 5a: Uncle 结点是黑色，并且插入结点是 left-outer-child(意味着其与 parent, grandparent 结点相连接后是一条直线)
            rotateRight(grandparent);

            // 重新上色
            parent.color = BLACK;
            grandparent.color = RED;
        } else {
            // Case 4b
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
            }

            // Case 5b
            rotateLeft(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }


    /**
     * case 1
     * case 2 -> case 3-6
     * case 3
     * case 4 -> case 1-3, 4-6
     * case 5 -> case 6
     * case 6
     * 只有不平衡状态处于 Case 1, 3, 6 时可以直接消除不平衡.
     * @param key
     */
    @Override
    public void deleteNode(int key) {
        Node node = root;

        // 找到目标结点.
        while (node != null && node.data != key) {
            if (key < node.data) {
                node = node.left;
            }else {
                node = node.right;
            }
        }

        // 未找目标结点.
        if (node == null) return;

        // 此时，node 结点就是待被删除的目标结点，但所实际删除的结点可能是其本身或其后继结点.

        // 这里我们存储那些 moved-up-node(代替实际被删除结点的那些结点) 和实际被删除结点的颜色.
        Node movedUpNode;
        Boolean deletedNodeColor;


        // Case 1: 删除其本身.
        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }
        // Case 2: 删除其后继结点.
        else {
            // 找到后继结点.
            Node inOrderSuccessor = findMinimum(node.right);
            // 用后继结点替换被删除的目标结点(只替换数据，不替换颜色).
            node.data = inOrderSuccessor.data;
            // 删除后继结点.
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        // 如果删除的是红色结点无所谓，如果是黑色则需要进行修复
        if (deletedNodeColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);

            // 删除临时展位的 NIL node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }

    }
    /**
     *
     * @param node
     * @return move-up-node
     */
    private Node deleteNodeWithZeroOrOneChild(Node node) {
        if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left);
            return node.left;
        }
        else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right;
        }
        else {
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }

    private Node findMinimum(Node node) {
        if (node.left != null) {
            return node.left;
        }
        return node;
    }
    private void fixRedBlackPropertiesAfterDelete(Node node) {
        // Case 1: 删除结点为根结点，结束递归.
        if (node == root) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return;
        }

        Node sibling = getSibling(node);

        // Case 2: sibling 结点颜色为 RED.
        if (sibling.color == RED) {
            // 当处理完后，某侧子树必定不平衡，会导致 Case3-6，所以此处不能返回，需要 fall-through cases 3-6.
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        // Case 3+4: sibling 结点是黑色，并且其由两个黑色子结点(包括叶结点).
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            // Case 3+4 都需要 sibling recolor
            sibling.color = RED;

            // Case 3: 父结点为红色.
            if (node.parent.color == RED) {
                node.parent.color = BLACK;
            }
            // Case 4: 父结点为黑色.
            else {
                // 从则也可以看出，Case 4 也不能消除不平衡，而是将该 case 排除. 然后假装删除(因为是递归调用) node.parent 结点，通过处理新 case 来自平衡.
                fixRedBlackPropertiesAfterDelete(node.parent);
            }
        }
        // Case 5+6: sibling 结点是黑色，并且其至少有一个 RED 子结点.
        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }

    }

    /**
     * Case 2 对应的操作并不能消除不平衡，而是为了将 sibling 为 RED 这种情况排除，新的不平衡处会对应 Case4-6.
     * @param node
     * @param sibling
     */
    private void handleRedSibling(Node node, Node sibling) {
        sibling.color = BLACK;
        node.parent.color = RED;

        // 根据结点位置进行旋转
        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }


    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;
        // Case 5:  outer-nephew 是黑色
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        // Fall-though to case 6, 因为 Case 5 上半部分的操作就是为了将不平衡状态转移到 Case 6.

        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild) {
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }
    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else  {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.right = node;
        node.parent = leftChild;
        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.left = node;
        node.parent = rightChild;
        replaceParentsChild(parent, node, rightChild);
    }



    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
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
        builder.append(node.data + (node.color ? "⬛" : "\uD83D\uDFE5"));
    }

}
