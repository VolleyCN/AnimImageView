package com.moses.lib.link;

public class LeetCode82 {
    public static void main(String[] args) {
        ListNode listNode = ListNode.of(1, 2, 3, 3, 4, 4, 5).print();
        deleteDuplicates(listNode).print();
    }

    public static ListNode deleteDuplicates(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        ListNode p1 = p;
        ListNode p2;
        while ((p2 = p1.next) != null) {
            if (p1.val == p2.val) {
                p1.next = p2.next;
            } else {
                p1 = p1.next;
            }
        }
        return p;
    }


    public static ListNode deleteDuplicates2(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        if (p.val == p.next.val) {
            p.next = deleteDuplicates2(p.next.next);
        } else {
            p.next = deleteDuplicates2(p.next);
        }
        return p;
    }


}
