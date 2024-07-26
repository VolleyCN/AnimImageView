package com.moses.lib.link;

public interface Queue<E> extends Iterable<E>{

    int size();

    boolean isEmpty();

    boolean offer(E element);

    E poll();

    boolean isFull();

    E peek();

    void print();
}
