package com.moses.lib.treenode;

import org.junit.Test;

public class Leecode1008 {

    @Test
    public void test() {
        int[] preorder = new int[]{8, 5, 1, 7, 10, 12};
        TreeNode root = bstFromPreorder(preorder);
        Leecode144.preOrder(root, System.out::println);
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        return partition1(preorder, Integer.MAX_VALUE);
    }

    public TreeNode partition(int[] preorder, int start, int end) {
        if (start > end) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[start]);
        int index = start + 1;
        while (index <= end) {
            if (preorder[index++] > root.val) {
                break;
            }
        }
        root.left = partition(preorder, start + 1, index-1);
        root.right = partition(preorder, index, end);
        return root;
    }

    private int index = 0;

    public TreeNode partition1(int[] preorder, int max) {
        if (index > preorder.length - 1) {
            return null;
        }
        if (preorder[index] > max) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[index]);
        index++;
        root.left = partition1(preorder, root.val);
        root.right = partition1(preorder, max);
        return root;
    }
}
