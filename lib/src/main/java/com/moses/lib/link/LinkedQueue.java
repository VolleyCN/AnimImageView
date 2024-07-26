package com.moses.lib.link;

import java.util.Iterator;
import java.util.Stack;

public class LinkedQueue<E> implements Queue<E> {
    private final Node head;
    private Node tail;
    private int size;
    private final int capacity;

    public LinkedQueue() {
        head = new Node(null, null);
        tail = head;
        tail.next = head;
        capacity = 10;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean offer(E element) {
        if (isFull()) {
            return false;
        }
        Node added = new Node(element, head);
        tail.next = added;
        tail = added;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            tail = head;
        }
        Node node = head.next;
        head.next = node.next;
        size--;
        return node.data;
    }

    @Override
    public boolean isFull() {
        return capacity == size;
    }

    @Override
    public E peek() {
        if (!isEmpty()) {
            return head.next.data;
        }
        return null;
    }


    @Override
    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        forEach(e -> sb.append(e).append(","));
        sb.append("]");
        System.out.println(sb);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node p = head.next;

            @Override
            public boolean hasNext() {
                return p != head;
            }

            @Override
            public E next() {
                E data = p.data;
                p = p.next;
                return data;
            }
        };
    }

    private class Node {
        E data;
        Node next;

        public Node(E data) {
            this.data = data;
        }

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

}
