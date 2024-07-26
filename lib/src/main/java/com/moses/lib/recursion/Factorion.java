package com.moses.lib.recursion;

public class Factorion {
    public static void main(String[] args) {
        System.out.println(f(5));
    }
    public static int f(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * f(n - 1);
        }
    }
}
