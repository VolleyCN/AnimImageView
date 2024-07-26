package com.moses.lib.link;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int x, ListNode next) {
        val = x;
        this.next = next;
    }

    public static ListNode of(int... arr) {
        ListNode head = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            ListNode node = new ListNode(arr[i]);
            node.next = head;
            head = node;
        }
        return head;
    }

    public ListNode print() {
        ListNode node = this;
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
        return this;
    }
}
