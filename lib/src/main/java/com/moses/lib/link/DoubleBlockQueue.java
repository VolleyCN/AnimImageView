package com.moses.lib.link;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
public class DoubleBlockQueue<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private final int mCapacity;
    private int mHead;
    private int mTail;
    private AtomicInteger mSize = new AtomicInteger(0);
    private E[] mQueue;
    private ReentrantLock mTailLock = new ReentrantLock();
    private ReentrantLock mHeadLock = new ReentrantLock();
    private Condition mHeadAwait = mHeadLock.newCondition();
    private Condition mTailAwait = mTailLock.newCondition();

    public DoubleBlockQueue() {
        this(DEFAULT_CAPACITY);
    }

    public DoubleBlockQueue(int capacity) {
        mCapacity = capacity;
        mQueue = (E[]) new Object[capacity];
    }

    public boolean offer(E e) throws InterruptedException {
        return offer(e, 0, null);
    }

    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        mTailLock.lockInterruptibly();
        int c;
        try {
            if (unit == null) {
                while (isFull()) {
                    mTailAwait.await();
                }
            } else {
                long nanos = unit.toNanos(timeout);
                while (isFull()) {
                    if (nanos <= 0) {//超时
                        return false;
                    }
                    nanos = mTailAwait.awaitNanos(nanos);
                }
            }
            mQueue[mTail] = e;
            if (++mTail == mCapacity) {
                mTail = 0;
            }
            c = mSize.getAndIncrement();// ++mSize;
            if (c + 1 < mCapacity) {//还没满，可以再唤醒 级联唤醒
                mTailAwait.signal();
            }
        } finally {
            mTailLock.unlock();
        }
        if (c == 0) {//如果队列为空，第一个，唤醒消费者 后续的 mHeadLock 内自己唤醒 从空到非空 可读取
            mHeadLock.lock();
            try {
                mHeadAwait.signal();
            } finally {
                mHeadLock.unlock();
            }
        }
        return true;
    }

    public E poll() throws InterruptedException {
        return poll(0, null);
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        mHeadLock.lockInterruptibly();
        E e;
        int c;
        try {
            if (unit == null) {
                if (isEmpty()) {
                    mHeadAwait.await();
                }
            } else {
                long nanos = unit.toNanos(timeout);
                if (isEmpty()) {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = mHeadAwait.awaitNanos(nanos);
                }
            }
            e = mQueue[mHead];
            mQueue[mHead] = null;
            if (++mHead == mCapacity) {
                mHead = 0;
            }
            c = mSize.getAndDecrement();
            if (c > 1) {//还没空，可以继续取 唤醒其他 mTailLock
                mHeadAwait.signal();
            }
        } finally {
            mHeadLock.unlock();
        }
        if (c == mCapacity) {//从满 到非满，唤醒生产者 可以添加
            mTailLock.lock();
            try {
                mTailAwait.signal();
            } finally {
                mTailLock.unlock();
            }
        }
        return e;
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        forEach(e -> sb.append(e).append(","));
        sb.append("]");
        System.out.println(sb.toString());
    }

    public boolean isEmpty() {
        return mSize.get() == 0;
    }

    public boolean isFull() {
        return mSize.get() == mCapacity;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = mHead;

            @Override
            public boolean hasNext() {
                return index != mTail;
            }

            @Override
            public E next() {
                E e = mQueue[index];
                if (++index == mCapacity) {
                    index = 0;
                }
                return e;
            }
        };
    }
}
