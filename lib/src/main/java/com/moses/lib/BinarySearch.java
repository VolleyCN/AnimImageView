package com.moses.lib;

public class BinarySearch {
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {//目标大于中间值，则目标在右边
                left = mid + 1;//左边界向右移动
            } else {
                right = mid + 1;//右边界向左移动
            }
        }
        return -1;
    }

    public static int binarySearchAlternative(int[] array, int target) {
        int left = 0;
        int right = array.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return -1;
    }

    public static int binarySearchBalance(int[] array, int target) {
        int left = 0;
        int right = array.length;
        while (1 < right - left) {
            int mid = (left + right) >>> 1;
            if (target < array[mid]) {//目标小于中间值，则目标在左边
                right = mid;//右边界向左移动
            } else {
                left = mid;//左边界向右移动
            }
        }
        if (array[left] == target) {
            return left;
        } else {
            return -1;
        }
    }
}
