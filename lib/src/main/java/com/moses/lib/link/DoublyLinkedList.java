package com.moses.lib.link;

import java.util.Iterator;

public class DoublyLinkedList implements Iterable<Integer> {
    private final Node head;
    private final Node tail;

    public DoublyLinkedList() {
        head = new Node(111);
        tail = new Node(999);
        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(int data) {
        Node next = head.next;
        Node node = new Node(data, head, next);
        head.next = node;
        next.prev = node;
    }

    public void addLast(int data) {
        Node prev = tail.prev;
        Node node = new Node(data, prev, tail);
        prev.next = node;
        tail.prev = node;
    }

    public void removeFirst() {
        Node next = head.next.next;
        head.next = next;
        next.prev = head;
    }

    public void removeLast() {
        Node removed = tail.prev;
        if (removed == head) {
            return;
        }
        Node prev = removed.prev;
        prev.next = tail;
        tail.prev = prev;
    }

    public void insert(int index, int data) {
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw new IndexOutOfBoundsException();
        }
        Node next = prev.next;
        Node node = new Node(data, prev, next);
        prev.next = node;
        next.prev = node;
    }

    public Integer remove(int index) {
        Node prev = findNode(index - 1);
        if (prev == null || prev.next == tail) {
            throw new IndexOutOfBoundsException(index);
        }
        Node remove = prev.next;
        Node next = remove.next;
        prev.next = next;
        next.prev = prev;
        return prev.data;
    }

    private Node findNode(int index) {
        Node p;
        for (p = head.next; p != tail; p = p.next, index--) {
            if (index == -1) {
                return p;
            }
        }
        return null;
    }

    public int get(int index) {
        Node p = findNode(index - 1);
        if (p == null) {
            throw new IndexOutOfBoundsException(index);
        }
        return p.data;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public Integer next() {
                int data = p.data;
                p = p.next;
                return data;
            }
        };
    }

    private static class Node {
        Node prev;
        int data;
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
