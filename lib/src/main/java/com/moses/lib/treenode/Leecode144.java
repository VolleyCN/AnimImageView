package com.moses.lib.treenode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Leecode144 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(4), 2, new TreeNode(7)),
                1,
                new TreeNode(new TreeNode(5), 3, new TreeNode(6)));
        preOrder(root, System.out::print);
        System.out.println("----------------------------------------------------------------------");
        inOrder(root, System.out::print);
        System.out.println("----------------------------------------------------------------------");
        postOrder(root, System.out::print);
        System.out.println("----------------------------------------------------------------------");

        preOrder2(root, System.out::print);
        System.out.println("----------------------------------------------------------------------");
        inOrder2(root, System.out::print);
        System.out.println("----------------------------------------------------------------------");
        postOrder2(root, System.out::print);

        System.out.println("----------------------------------------------------------------------");
        levelOrder(root).forEach(System.out::println);
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> level = new ArrayList<>();
        if (root == null) {
            return level;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                l.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            level.add(l);
        }
        return level;
    }

    public static void preOrder(TreeNode root, Consumer<TreeNode> consumer) {
        if (root == null) {
            return;
        }
        consumer.accept(root);
        preOrder(root.left, consumer);
        preOrder(root.right, consumer);
    }

    public static void inOrder(TreeNode root, Consumer<TreeNode> consumer) {
        if (root == null) {
            return;
        }
        inOrder(root.left, consumer);
        consumer.accept(root);
        inOrder(root.right, consumer);
    }

    public static void postOrder(TreeNode root, Consumer<TreeNode> consumer) {
        if (root == null) {
            return;
        }
        postOrder(root.left, consumer);
        postOrder(root.right, consumer);
        consumer.accept(root);
    }

    public static void preOrder2(TreeNode root, Consumer<TreeNode> consumer) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {//处理左子树
                consumer.accept(cur);
                stack.push(cur);
                cur = cur.left;
            } else {//处理右子树
                TreeNode pop = stack.pop();
                cur = pop.right;
            }
        }
    }

    public static void inOrder2(TreeNode root, Consumer<TreeNode> consumer) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {//处理左子树
                stack.push(cur);
                cur = cur.left;
            } else {//处理右子树
                TreeNode pop = stack.pop();
                consumer.accept(pop);
                cur = pop.right;
            }
        }
    }

    public static void postOrder2(TreeNode root, Consumer<TreeNode> consumer) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode pop = null;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {//处理左子树
                consumer.accept(cur);//前序遍历

                stack.push(cur);
                cur = cur.left;//处理左子树
            } else {//处理右子树
                TreeNode peek = stack.peek();// 栈顶元素

                if (peek.right == null) { //没有右子树
                    consumer.accept(peek);//中序遍历

                    pop = stack.pop();// 弹出栈顶元素
                    consumer.accept(pop); // 后序遍历
                } else if (peek.right == pop) {// 右子树处理完成
                    pop = stack.pop();// 弹出栈顶元素
                    consumer.accept(pop);// 后序遍历
                } else {

                    consumer.accept(peek);//中序遍历
                    cur = peek.right;// 处理右子树

                }
            }
        }
    }
}
