package com.moses.lib.recursion;

public class ReversePrintString {
    public static void main(String[] args) {
        reversePrint("moses", 0);
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(f(a, 11, 0, a.length - 1));
    }

    public static void reversePrint(String str, int index) {
        if (index == str.length()) {
            return;
        }
        reversePrint(str, index + 1);
        System.out.println(str.charAt(index));
    }


    public static int f(int[] a, int target, int i, int j) {
        if (i > j) {
            return -1;
        }
        int m = (i + j) >>> 1;
        if (target < a[m]) {
            j = m - 1;
        } else if (target > a[m]) {
            i = m + 1;
        } else {
            return m;
        }
        return f(a, target, i, j);
    }
}
