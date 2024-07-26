package com.moses.lib.recursion;

public class ReverseNodeList {

    public static void main(String[] args) {
    }

    public static Node reverse(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null;
        while (head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    private static class ListNode {
        Node head;

        public ListNode(Node head) {
            this.head = head;
        }

        public void addFirst(int data) {
            Node node = new Node(data);
            node.next = head;
            head = node;
        }

        public Node removeFirst() {
            Node node = head;
            head = head.next;
            return node;
        }
    }

    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
