package com.moses.lib.link;

import java.util.ArrayList;

public class Leecode234 {
    public static void main(String[] args) {
    }

    public static ListNode reverse(ListNode o1) {
        ListNode n1 = null;
        while (o1 != null){
            ListNode o2 = o1.next;//1->2->3->4->5->null
            o1.next = n1;//1->null
            n1 = o1;//1->2->3->4->5->null
            o1 = o2;//2->3->4->5->null
        }
        return n1;
    }

    public static ListNode middle(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
