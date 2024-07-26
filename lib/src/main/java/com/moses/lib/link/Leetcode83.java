package com.moses.lib.link;

public class Leetcode83 {
    public static void main(String[] args) {
        ListNode listNode = ListNode.of(1, 1, 2, 3, 3, 3, 4, 5, 6, 7, 7, 7).print();
        deleteDuplicates2(listNode).print();
    }

    public static ListNode deleteDuplicates(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        if (p.val == p.next.val) {
            ListNode x = p.next;
            while (x != null && p.val == x.val) {
                x = x.next;
            }
            return deleteDuplicates(x);
        } else {
            p.next = deleteDuplicates(p.next);
        }
        return p;
    }

    public static ListNode deleteDuplicates2(ListNode p) {
        if (p == null || p.next == null) {
            return p;
        }
        ListNode s = new ListNode(999, p);
        ListNode p1 = s;
        ListNode p2, p3;
        while ((p2 = p1.next) != null && (p3 = p2.next) != null) {
            if (p2.val == p3.val) {
                while ((p3 = p3.next) != null && p2.val == p3.val) ;
                p1.next = p3;
            } else {
                p1 = p1.next;
            }
        }
        return s.next;
    }
}
