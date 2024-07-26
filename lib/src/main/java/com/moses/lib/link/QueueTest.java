package com.moses.lib.link;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.concurrent.BlockingDeque;

public class QueueTest {
    public static void main(String[] args) {
    }

    @Test
    public void testQueue() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.print();
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    @Test
    public void testDeque() {
        LinkedDeque<Integer> deque = new LinkedDeque<>(3);
        deque.offerFirst(1);
        deque.offerFirst(2);
        deque.offerFirst(3);
        deque.offerFirst(4);
        deque.print();
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        deque.print();

        deque.offerLast(1);
        deque.offerLast(2);
        deque.offerLast(3);
        deque.offerLast(4);
        deque.print();
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        deque.print();

    }

    @Test
    public void testDeque2() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);
        deque.offerFirst(1);
        deque.offerFirst(2);
        deque.offerFirst(3);
        deque.offerFirst(4);

        deque.print();
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        deque.print();

        deque.offerLast(1);
        deque.offerLast(2);
        deque.offerLast(3);
        deque.offerLast(4);
        deque.print();
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        deque.print();
    }

    @Test
    public void testBlockQueue() {
        BlockQueue<Integer> queue = new BlockQueue<>(5);
        new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                queue.offer(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.offer(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.offer(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                queue.offer(6);
                queue.print();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            if (queue.isFull()) {
                try {
                    queue.poll();
                    queue.print();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testBlockQueue2() {
        DoubleBlockQueue<Integer> queue = new DoubleBlockQueue<>(5);
        int count = 0;
        while (true) {
            if (queue.isEmpty() && count < 2) {
                for (int i = 1; i < 10; i++) {
                    int finalI = i;
                    new Thread(() -> {
                        try {
                            if (finalI % 2 == 0) {
                                System.out.println(queue.offer(finalI));
                            } else {
                                System.out.println(queue.offer(finalI, 10000, java.util.concurrent.TimeUnit.MILLISECONDS));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (queue.isFull() && count < 2) {
                for (int i = 1; i < 10; i++) {
                    int finalI = i;
                    new Thread(() -> {
                        try {
                            if (finalI % 2 == 0) {
                                queue.poll();
                            } else {
                                queue.poll(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
                            }
                            queue.print();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
            if (count < 3) {
                count++;
            }
        }
    }

}
