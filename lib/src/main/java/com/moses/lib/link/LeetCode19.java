package com.moses.lib.link;

public class LeetCode19 {
    public static void main(String[] args) {

        ListNode listNode = ListNode.of(1, 2, 3, 4, 5);
//        ListNode listNode1 = removeNthFromEnd(listNode, 2);
//        listNode1.print();

//        ListNode listNode2 = removeNthFromEnd(listNode, 5);
//        listNode2.print();

        ListNode listNode3 = removeNthFromEnd2(listNode, 5);
        listNode3.print();
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode s = new ListNode(999, head);
        s.next = head;
        remove(s, n);
        return s.next;
    }

    public static int remove(ListNode p, int n) {
        if (p == null) {
            return 0;
        }
        int nth = remove(p.next, n);
        System.out.print(nth + "----->");
        if (nth == n) {
            p.next = p.next.next;
        }
        System.out.println(p.val);
        return nth + 1;
    }

    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode s = new ListNode(999, head);
        ListNode p1 = s;
        ListNode p2 = s;
        for (int i = 0; i < n + 1; i++) {
            p2 = p2.next;
        }
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        p1.next = p1.next.next;
        return s.next;
    }
}
