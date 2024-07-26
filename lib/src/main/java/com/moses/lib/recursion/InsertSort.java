package com.moses.lib.recursion;

public class InsertSort {
    public static void main(String[] args) {

    }

    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        }
    }

    public static void insert(int[] a, int low) {
        if (low == a.length) {//递归结束条件
            return;
        }
        int temp = a[low];//待插入元素
        while (low - 1 >= 0 && a[low - 1] > temp) {//找到插入位置
            a[low] = a[low - 1];
            low--;
        }
        a[low] = temp;
        insert(a, low + 1);
    }
}
