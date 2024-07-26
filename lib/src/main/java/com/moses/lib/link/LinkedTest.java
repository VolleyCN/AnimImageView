package com.moses.lib.link;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;


public class LinkedTest {
    @Test
    public void test() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);
        assertEquals(5, list.get(0));
        assertEquals(1, list.get(4));

        list.forEach(integer -> {
            System.out.println(" " + integer);
        });
    }

    @Test
    public void test1() {
        SinglyLinkedList list = new SinglyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        assertEquals(1, list.get(0));
        assertEquals(5, list.get(4));
        list.insert(3, 6);
        list.insert(0, 7);
        list.insert(7, 8);

        assertEquals(7, list.get(0));
        assertEquals(8, list.get(7));


        list.forEach(integer -> System.out.println(" " + integer));
        list.remove(7);
        list.remove(3);
        list.remove(0);
        System.out.println(list);
    }

    @Test
    public void test2() {
        DoubleLinkedList list = new DoubleLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);
        System.out.println(list);
    }

    @Test
    public void test3() {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.forEach(integer -> System.out.println(" " + integer));
        try {
            assertEquals(4, list.get(-1));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Index out of range: "+-1);
        }
        try {
            assertEquals(1, list.get(3));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "IndexOutOfBoundsException");
        }

    }

    @Test
    public void test4() {
        SinglyLinkedListSentinel list = new SinglyLinkedListSentinel();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.forEach(integer -> System.out.println(" " + integer));
         System.out.println(list);
    }
}
