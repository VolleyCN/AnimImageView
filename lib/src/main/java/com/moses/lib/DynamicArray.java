package com.moses.lib;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.function.Consumer;

public class DynamicArray implements Iterable<Integer> {
    private Integer[] mArray;
    private int mSize;
    private final int mCapacity;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int defaultCapacity) {
        mCapacity = defaultCapacity;
    }

    public void addLast(Integer data) {
        add(mSize, data);
    }

    public Integer remove(int index) {
        if (index < 0 || index >= mSize) {
            throw new IllegalArgumentException("index is illegal");
        }
        Integer ret = mArray[index];
        System.arraycopy(mArray, index + 1, mArray, index, mSize - index - 1);
        mArray[mSize - 1] = null;
        mSize--;
        return ret;
    }

    private void checkCapacity() {
        if (mSize == 0) {
            mArray = new Integer[mCapacity];
        } else if (mSize == mCapacity) {
            resize(mCapacity * 2);
        }
    }

    public void addFirst(int data) {
        add(0, data);
    }

    public void add(int index, int data) {
        if (index < 0 || index > mSize) {
            throw new IllegalArgumentException("index is illegal");
        }
        checkCapacity();
        System.arraycopy(mArray, index, mArray, index + 1, mSize - index);
        mArray[index] = data;
        mSize++;
    }

    private void resize(int i) {
        Integer[] newArray = new Integer[i];
        System.arraycopy(mArray, 0, newArray, 0, mSize);
        mArray = newArray;
    }

    public int get(int index) {
        if (index < 0 || index >= mSize) {
            throw new IllegalArgumentException("index is illegal");
        }
        return mArray[index];
    }

    public void print() {
        for (int i = 0; i < mSize; i++) {
            System.out.print(mArray[i] + " ");
        }
    }

    public void foreach(Consumer<? super Integer> consumer) {
        for (int i = 0; i < mSize; i++) {
            consumer.accept(mArray[i]);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int mIndex = 0;

            @Override
            public boolean hasNext() {
                return mIndex < mSize;
            }

            @Override
            public Integer next() {
                return mArray[mIndex++];
            }
        };
    }
}
