package com.moses.lib.link;

import org.junit.Test;

public class HeapTest {
    @Test
    public void test() {
        MaxHeap heap = new MaxHeap();
        heap.insert(1);
        heap.insert(2);
        heap.insert(5);
        heap.insert(4);
        heap.insert(3);
        heap.insert(8);
        heap.insert(7);
        heap.insert(6);
        heap.print();

        while (!heap.isEmpty()) {
            System.out.println(heap.remove());
        }

        int[] data = {1, 2, 5, 4, 3, 8};
        heap = new MaxHeap(data);
        heap.print();
        while (!heap.isEmpty()) {
            System.out.println(heap.remove());
        }

    }

    @Test
    public void test2() {
        Heap<Integer> heap = new Heap<>();
        heap.insert(1);
        heap.insert(2);
        heap.insert(5);
        heap.insert(4);
        heap.insert(3);
        heap.insert(8);
        heap.insert(7);
        heap.insert(6);
        heap.print();

        while (!heap.isEmpty()) {
            System.out.println(heap.remove());
        }

        Integer[] data = {1, 2, 5, 7, 4, 3, 8, 6};
        heap = new Heap<>(data, false);
        heap.print();
        heap.insert(11);
        heap.insert(8);
        heap.insert(10);
        heap.insert(9);
        while (!heap.isEmpty()) {
            System.out.println(heap.remove());
        }
    }
}
