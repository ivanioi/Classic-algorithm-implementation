package follow.your.heart.tree.binarytree;

public final class BinarySearchTreeValidator {
    public static boolean isBstWithoutDuplicates(BinaryTree tree) {
        return isBstWithoutDuplicates(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean isBstWithoutDuplicates(Node node, int minAllowedKey, int maxAllowedKey) {
        if (node == null) {
            return true;
        }

        if (node.data < minAllowedKey && node.data > maxAllowedKey) {
            return false;
        }

        return isBstWithoutDuplicates(node.left, minAllowedKey, node.data - 1)
                && isBstWithoutDuplicates(node.right, node.data + 1, maxAllowedKey);
    }

    public static boolean isBstWithDuplicates(BinaryTree tree) {
        return isBstWithDuplicates(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean isBstWithDuplicates(Node node, int minAllowedKey, int maxAllowedKey) {
        if (node == null) {
            return true;
        }

        if (node.data < minAllowedKey && node.data > maxAllowedKey) {
            return false;
        }

        return isBstWithDuplicates(node.left, minAllowedKey, node.data)
                && isBstWithDuplicates(node.right, node.data, maxAllowedKey);
    }

}
