package com.moses.lib.link;

import java.util.Iterator;

public class DoubleLinkedList implements Iterable<Integer> {
    public Node head;
    public Node tail;
    public int size;

    public void addFirst(int data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
        } else {
            Node node = new Node(data);
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Integer next() {
                int data = node.data;
                node = node.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        forEach(integer -> sb.append(integer).append(","));
        sb.append("]");
        return sb.toString();
    }

    public static class Node {
        int data;
        Node prev;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
