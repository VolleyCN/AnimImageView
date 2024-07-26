package com.moses.lib.link;

import java.util.Iterator;

public class LinkedDeque<E> implements Deque<E> {
    private final Node<E> sentinel;
    private final int capacity;
    private int size;

    public LinkedDeque(int capacity) {
        this.capacity = capacity;
        sentinel = new Node<>(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedDeque() {
        this(Integer.MAX_VALUE);
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean offerFirst(E element) {
        if (isFull()) {
            return false;
        }
        Node<E> preFirst = sentinel.next;
        Node<E> added = new Node<>(element, sentinel, sentinel.next);
        sentinel.next = added;
        preFirst.prev = added;
        size++;
        return true;
    }

    @Override
    public boolean offerLast(E element) {
        if (isFull()) {
            return false;
        }
        Node<E> preLast = sentinel.prev;
        Node<E> added = new Node<>(element, sentinel.prev, sentinel);
        preLast.next = added;
        sentinel.prev = added;
        size++;
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> removed = sentinel.next;
        Node<E> prev = removed.prev;
        Node<E> next = removed.next;
        prev.next = next;
        next.prev = prev;
        size--;
        return removed.data;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        Node<E> remove = sentinel.prev;
        Node<E> prev = remove.prev;
        Node<E> next = remove.next;
        prev.next = next;
        next.prev = prev;
        size--;
        return remove.data;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.next.data;
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return sentinel.prev.data;
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
        return new Iterator<>() {
            Node<E> curr = sentinel.next;

            @Override
            public boolean hasNext() {
                return curr != sentinel;
            }

            @Override
            public E next() {
                E data = curr.data;
                curr = curr.next;
                return data;
            }
        };
    }

    private static class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        public Node(E data) {
            this.data = data;
        }

        public Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
