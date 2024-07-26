package com.moses.lib.treenode;

import org.junit.Assert;
import org.junit.Test;

public class TestBSTTree {
    @Test
    public void test() {
//        BSTTree<Integer, String> tree = createTree();
        BSTTree<Integer, String> tree = createTree2();
        Assert.assertEquals(tree.get(1), "1");
        Assert.assertEquals(tree.get(2), "2");
        Assert.assertEquals(tree.get(3), "3");
        Assert.assertEquals(tree.get(4), "4");
        Assert.assertEquals(tree.get(5), "5");
        Assert.assertNull(tree.get(10));
        Assert.assertEquals(tree.max(), 8 + "");
        Assert.assertEquals(tree.min(), 1 + "");

        BSTTree<Integer, String> tree1 = createTree();
        BSTTree<Integer, String> tree3 = createTree3();


    }

    @Test
    public void test2() {
        BSTTree<Integer, String> tree = createTree2();
        String s = tree.remove2(4);
        tree.print();
    }

    private BSTTree<Integer, String> createTree() {
        BSTTree<Integer, String> tree = new BSTTree<>();
        tree.put(4, "4");
        tree.put(1, "1");
        tree.put(2, "2");
        tree.put(8, "8");
        tree.put(7, "7");
        tree.put(3, "3");
        tree.put(5, "5");
        tree.put(6, "6");
        tree.print();
        return tree;
    }

    private BSTTree<Integer, String> createTree3() {
        BSTTree<Integer, String> tree = new BSTTree<>();
        for (int i = 1; i <= 8; i++) {
            tree.put(i, i + "");
        }
        tree.print();
        return tree;
    }

    private BSTTree<Integer, String> createTree2() {
        BSTTree.BSTNode<Integer, String> root = new BSTTree.BSTNode<>(4, "4");
        root.left = new BSTTree.BSTNode<>(2, "2");
        root.right = new BSTTree.BSTNode<>(7, "7");
        root.left.left = new BSTTree.BSTNode<>(1, "1");
        root.left.right = new BSTTree.BSTNode<>(3, "3");
        root.right.left = new BSTTree.BSTNode<>(6, "6");
        root.right.right = new BSTTree.BSTNode<>(8, "8");
        root.right.left.left = new BSTTree.BSTNode<>(5, "5");
        BSTTree<Integer, String> tree = new BSTTree<>(root);
        tree.print();
        return tree;
    }

}
