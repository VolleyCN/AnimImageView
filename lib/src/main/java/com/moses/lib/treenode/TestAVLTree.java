package com.moses.lib.treenode;

import org.junit.Test;

public class TestAVLTree {
    @Test
    public void test() {
        AVLTree<Integer, String> avlTree = new AVLTree<>();
        avlTree.put(2, "2");
        avlTree.put(1, "1");
        avlTree.put(3, "3");
        avlTree.put(11, "11");
        avlTree.put(5, "5");
        avlTree.put(4, "4");
        avlTree.put(7, "7");
        avlTree.put(10, "10");
        avlTree.put(6, "6");
        avlTree.put(9, "9");
        avlTree.put(8, "8");
        avlTree.print();
        avlTree.remove(6);
        avlTree.print();
    }
}
