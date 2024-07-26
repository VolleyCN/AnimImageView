package com.moses.lib.link;

import java.util.Iterator;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
public class SinglyLinkedListSentinel implements Iterable<Integer> {
    private Node sentinel;

    public SinglyLinkedListSentinel() {
        sentinel = new Node(0);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node cur = sentinel.next;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Integer next() {
                int data = cur.data;
                cur = cur.next;
                return data;
            }
        };
    }

    public static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public void addFirst(int data) {
        sentinel.next = new Node(data, sentinel.next);
    }

    public void addLast(int data) {
        Node cur = sentinel;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new Node(data);
    }

    public void insert(int index, int data) {
        if (index < 0) {
            throw new IllegalArgumentException("index must >= 0");
        }
        Node preNode = findNode(index - 1);
        if (preNode == null) {
            throw new IllegalArgumentException("index is out of bound");
        }
        Node node = new Node(data);
        node.next = preNode.next;
        preNode.next = node;
    }

    public Integer remove(int index) {
        if (index < 0) {
            return null;
        }
        Node node = findNode(index - 1);
        if (node == null) {
            return null;
        } else {
            Node cur = node.next;
            node.next = cur.next;
            return cur.data;
        }
    }

    public int get(int index) {
        return findNode(index).data;
    }

    private Node findNode(int index) {
        Node cur;
        for (cur = sentinel; cur != null; cur = cur.next, index--) {
            if (index == -1) {
                return cur;
            }
        }
        return cur;
    }


    public void recursion(Node node, Consumer before, Consumer after) {
        if (node == null) {//递归结束条件
            return;
        }
        before.accept(node.data);//
        recursion(node.next, before, after);//递归调用
        after.accept(node.data);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        recursion(sentinel.next, (data) -> sb.append(data).append(","), (data) -> {

        });
        sb.append("]");
        return sb.toString();
    }
}
