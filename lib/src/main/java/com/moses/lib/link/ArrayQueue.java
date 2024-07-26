package com.moses.lib.link;

import java.util.Iterator;

public class ArrayQueue<E> implements Queue<E> {
    private final E[] array;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        array = (E[]) new Object[capacity + 1];
        head = 0;
        tail = 0;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return tail == head;
    }

    @Override
    public boolean offer(E element) {
        if (!isFull()) {
            array[tail] = element;
            tail = (tail + 1) % array.length;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E element = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return element;
    }

    @Override
    public boolean isFull() {
        return (tail + 1) % array.length == head;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return array[head];
    }


    @Override
    public void print() {

    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            int index = head;

            @Override
            public boolean hasNext() {
                return index != tail;
            }

            @Override
            public E next() {
                E element = array[index];
                index = (index + 1) % array.length;
                return element;
            }
        };
    }
}
