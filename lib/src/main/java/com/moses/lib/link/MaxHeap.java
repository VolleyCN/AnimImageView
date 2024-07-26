package com.moses.lib.link;

public class MaxHeap {
    private final int[] heap;
    private int size;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        heap = new int[capacity];
    }

    public MaxHeap(int[] data) {
        this.capacity = data.length;
        heap = data;
        size = data.length;
        heapify();
    }

    private void heapify() {
        for (int i = (size - 1) / 2; i >= 0; i--) {// 从最后一个非叶子节点开始
            siftDown(i);// 下滤
        }
    }

    private void siftDown(int i) {
        int max = i;
        int left = 2 * i + 1;// 左子节点
        int right = left + 1;// 右子节点
        if (left < size && heap[left] > heap[max]) {// 左子节点大于父节点
            max = left;
        }
        if (right < size && heap[right] > heap[max]) {// 右子节点大于父节点
            max = right;
        }
        if (max != i) {
            swap(i, max);
            siftDown(max);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void replace(int data) {
        heap[0] = data;
        siftDown(0);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void insert(int data) {
        if (isFull()) {
            throw new RuntimeException("heap is full");
        }
        heap[size++] = data;
        siftUp(size - 1);
    }

    private void siftUp(int i) {
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public int remove() {
        if (isEmpty()) {
            throw new RuntimeException("heap is empty");
        }
        int max = heap[0];// 最大值
        heap[0] = heap[--size];// 将最后一个元素放到堆顶
        siftDown(0);// 下滤
        return max;
    }
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
