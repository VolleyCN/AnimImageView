package com.moses.lib;

import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.util.LinkedList;


public class DataDemo {
    @Test
    public void testBinarySearch() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assert BinarySearch.binarySearch(array, 5) == 4;
        assert BinarySearch.binarySearch(array, 1) == 0;
        assert BinarySearch.binarySearch(array, 10) == 9;
        assert BinarySearch.binarySearch(array, 11) == -1;
    }

    @Test
    public void testBinarySearch1() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assert BinarySearch.binarySearch(array, 5) == 4;
        assert BinarySearch.binarySearch(array, 1) == 0;
        assert BinarySearch.binarySearch(array, 10) == 9;
        assert BinarySearch.binarySearch(array, 11) == -1;
    }

    @Test
    public void testDynamicArray() {
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.addLast(1);
        dynamicArray.addLast(2);
        dynamicArray.addLast(3);
        dynamicArray.addLast(4);
        dynamicArray.addLast(5);
//        dynamicArray.print();
        dynamicArray.add(2, 6);
//        dynamicArray.forEach((data) -> System.out.println(" " + data));
        for (Integer data: dynamicArray) {
            System.out.println(" " + data);
        }
        System.out.println(dynamicArray);
    }
}