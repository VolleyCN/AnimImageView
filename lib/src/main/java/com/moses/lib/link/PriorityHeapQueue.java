package com.moses.lib.link;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityHeapQueue {
    private Priority[] heap;
    private int size;
    private int capacity;
    private int maxSize;
    private int maxPriority;

    ReentrantLock lock = new ReentrantLock();
    Condition condition =  lock.newCondition();
}
