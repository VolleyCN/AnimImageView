package com.moses.lib.link;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("ALL")
public class BlockQueue<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private final int mCapacity;
    private int mHead;
    private int mTail;
    private int mSize;
    private E[] mQueue;
    private ReentrantLock mLock = new ReentrantLock();
    private Condition mHeadAwait = mLock.newCondition();
    private Condition mTailAwait = mLock.newCondition();

    public BlockQueue() {
        this(DEFAULT_CAPACITY);
    }

    public BlockQueue(int capacity) {
        mCapacity = capacity;
        mQueue = (E[]) new Object[capacity];
    }

    public boolean offer(E e) throws InterruptedException {
        mLock.lock();
        try {
            while (isFull()) {
                mTailAwait.await();
            }
            mQueue[mTail] = e;
            if (++mTail > mCapacity - 1) {
                mTail = 0;
            }
            mSize++;
            System.out.println("mTail " + mTail + "," + mSize);
            mHeadAwait.signal();
            return true;
        } finally {
            mLock.unlock();
        }
    }

    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        mLock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (isFull()) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = mTailAwait.awaitNanos(nanos);
            }
            mQueue[mTail] = e;
            if (++mTail > mCapacity - 1) {
                mTail = 0;
            }
            mSize++;
            mHeadAwait.signal();
            return true;
        } finally {
            mLock.unlock();
        }
    }

    public E poll() throws InterruptedException {
        mLock.lock();
        try {
            while (isEmpty()) {
                mHeadAwait.await();
            }
            E e = mQueue[mHead];
            mQueue[mHead] = null;
            if (++mHead > mCapacity - 1) {
                mHead = 0;
            }
            mSize--;
            mTailAwait.signal();
            return e;
        } finally {
            mLock.unlock();
        }
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        mLock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (isEmpty()) {
                if (nanos <= 0) {
                    return null;
                }
                nanos = mHeadAwait.awaitNanos(nanos);
            }
            E e = mQueue[mHead];
            mQueue[mHead] = null;
            if (++mHead > mCapacity - 1) {
                mHead = 0;
            }
            mSize--;
            mTailAwait.signal();
            return e;
        } finally {
            mLock.unlock();
        }
    }

    public void print() {
        mLock.lock();
        try {
            for (int i = mHead; i < mCapacity; i++) {
                System.out.print(mQueue[i] + " ");
            }
            System.out.println();
        } finally {
            mLock.unlock();
        }
    }

    public boolean isEmpty() {
        return mSize == 0;
    }

    public boolean isFull() {
        return mSize == mCapacity;
    }
}
