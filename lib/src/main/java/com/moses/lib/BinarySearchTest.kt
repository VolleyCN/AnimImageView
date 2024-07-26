package com.moses.lib



class BinarySearchTest {
    fun binarySearch(arr: IntArray, target: Int): Int {
        var low = 0
        var high = arr.size - 1
        while (low <= high) {
            val mid = (low + high) / 2
            if (arr[mid] == target) {
                return mid
            } else if (arr[mid] < target) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        return -1
    }

    fun binarySearchRecursive(arr: IntArray, target: Int, low: Int, high: Int): Int {
        if (low > high) {
            return -1
        }
        val mid = (low + high) / 2
        if (arr[mid] == target) {
            return mid
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, high)
        } else {
            return binarySearchRecursive(arr, target, low, mid - 1)
        }
    }

}
