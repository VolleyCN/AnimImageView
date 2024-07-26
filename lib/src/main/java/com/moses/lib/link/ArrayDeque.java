package com.moses.lib.link;

import java.util.Iterator;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
public class ArrayDeque<E> implements Deque<E> {
    private final int capacity;
    private int size;
    private E[] elements;
    private int head;
    private int tail;

    public ArrayDeque() {
        this(10);
    }

    public ArrayDeque(int capacity) {
        this.capacity = capacity;
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        if (!isFull()) {
            head = (head - 1 + capacity) % capacity;
            elements[head] = element;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean offerLast(E element) {
        if (!isFull()) {
            elements[tail] = element;
            tail = (tail + 1) % capacity;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public E pollFirst() {
        if (!isEmpty()) {
            E element = elements[head];
            elements[head] = null;
            head = (head + 1 + capacity) % capacity;
            size--;
            return element;
        }
        return null;
    }

    @Override
    public E pollLast() {
        if (!isEmpty()) {
            tail = (tail - 1) % capacity;
            E element = elements[tail];
            elements[tail] = null;
            size--;
            return element;
        }
        return null;
    }

    @Override
    public E peekFirst() {
        if (!isEmpty()) {
            return elements[head];
        }
        return null;
    }

    @Override
    public E peekLast() {
        if (!isEmpty()) {
            return elements[tail];
        }
        return null;
    }

    @Override
    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        forEach(new Consumer<E>() {
            @Override
            public void accept(E e) {
                sb.append(e).append(",");
            }
        });
        sb.append("]");
        System.out.println(sb);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = head;

            @Override
            public boolean hasNext() {
                return index != tail;
            }

            @Override
            public E next() {
                E element = elements[index];
                index = (index + 1) % capacity;
                return element;
            }
        };
    }
}
