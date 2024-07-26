package com.moses.lib.link;

import java.util.Iterator;

@SuppressWarnings("ALL")
public class SinglyLinkedList implements Iterable<Integer> {
    private Node head;

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node cur = head;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Integer next() {
                if (cur == null) {
                    return null;
                }
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
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }

    public void addLast(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
    }

    public void insert(int index, int data) {
        if (index < 0) {
            throw new IllegalArgumentException("index must >= 0");
        }
        if (index == 0) {
            addFirst(data);
        } else {
            Node preNode = findNode(index - 1);
            if (preNode == null) {
                throw new IllegalArgumentException("index is out of bound");
            }
            Node node = new Node(data);
            node.next = preNode.next;
            preNode.next = node;
        }
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
        if (index < 0) {
            return null;
        }
        if (index == 0) {
            if (head == null) {
                return null;
            }
            return head;
        } else {
            if (head.next == null) {
                return null;
            }
            Node cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node cur = head;
        while (cur != null) {
            sb.append(cur.data);
            if (cur.next != null) {
                sb.append(",");
            }
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
