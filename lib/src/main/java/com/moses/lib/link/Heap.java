package com.moses.lib.link;

import java.util.Comparator;

@SuppressWarnings("all")
public class Heap<E> {
    private final int capacity;
    private int size;
    private final E[] data;
    private final Comparator<E> comparator;
    private static final int DEFAULT_CAPACITY = 10;
    private final boolean isMaxHeap;

    public Heap() {
        this(DEFAULT_CAPACITY);
    }

    public Heap(int capacity) {
        this(capacity, true, null);
    }

    public Heap(int capacity, boolean isMaxHeap, Comparator<E> comparator) {
        this((E[]) new Object[capacity], isMaxHeap, comparator);
        this.size = 0;
    }

    public Heap(E[] data) {
        this(data, true, null);
    }

    public Heap(E[] data, boolean isMaxHeap) {
        this(data, isMaxHeap, null);
    }

    public Heap(E[] data, boolean isMaxHeap, Comparator<E> comparator) {
        this.capacity = data.length;
        this.data = data;
        this.size = data.length;
        this.isMaxHeap = isMaxHeap;
        this.comparator = comparator != null ? comparator : (e, t1) -> {
            if (e instanceof Comparable && t1 instanceof Comparable) {
                return ((Comparable) e).compareTo(t1);
            }
            return 0;
        };
        heapify();
    }

    private void heapify() {
        for (int i = (size - 1) / 2; i >= 0; i--) {// 从最后一个非叶子节点开始
            siftDown(i);// 下滤
        }
    }

    private void siftDown(int i) {
        int parent = i;
        int left = 2 * i + 1;
        int right = left + 1;
        if (left < size && (isMaxHeap ? comparator.compare(data[left], data[parent]) > 0 : comparator.compare(data[left], data[parent]) < 0)) {
            parent = left;
        }
        if (right < size && (isMaxHeap ? comparator.compare(data[right], data[parent]) > 0 : comparator.compare(data[right], data[parent]) < 0)) {
            parent = right;
        }
        if (parent != i) {
            swap(i, parent);
            siftDown(parent);
        }
    }

    private void siftUp(int i) {
        if (i <= 0) {
            return;
        }
        int parent = (i - 1) / 2;
        if (isMaxHeap ? comparator.compare(data[i], data[parent]) > 0 : comparator.compare(data[i], data[parent]) < 0) {
            swap(i, parent);
            siftUp(parent);
        }
    }

    private void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public void replace(E data) {
        this.data[0] = data;
        if (!isEmpty()) {
            siftDown(0);
        }
    }

    public void insert(E data) {
        if (isFull()) {
            throw new IllegalStateException("heap is full");
        }
        this.data[size++] = data;
        if (!isEmpty()) {
            siftUp(size - 1);
        }
    }

    public E remove() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            return data[--size];
        }
        E temp = data[0];
        data[0] = data[--size];
        siftDown(0);
        return temp;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

}
