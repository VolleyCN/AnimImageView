package com.moses.lib.link;

public interface Deque<E> extends Iterable<E>{
    boolean isEmpty();
    int size();
    boolean isFull();
    boolean offerFirst(E element);
    boolean offerLast(E element);
    E pollFirst();
    E pollLast();
    E peekFirst();
    E peekLast();
    void print();
}
