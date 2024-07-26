package com.moses.lib.treenode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 平衡二叉搜索
 */
public class AVLTree<K extends Comparable<K>, V> {
    static class AVLNode<K, V> {
        V value;
        K key;
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int height;

        AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    AVLNode<K, V> root;

    public void put(K key, V val) {
        root = doPut(root, key, val);
    }

    private AVLNode<K, V> doPut(AVLNode<K, V> node, K key, V val) {//插入
        if (node == null) {
            return new AVLNode<>(key, val);
        }
        if (key.compareTo(node.key) < 0) {//左子树
            node.left = doPut(node.left, key, val);//递归插入
        } else if (key.compareTo(node.key) > 0) {//右子树
            node.right = doPut(node.right, key, val);// 递归插入
        } else {
            node.value = val;
        }
        updateHeight(node);
        return balance(node);
    }

    public void remove(K key) {//删除
        root = doRemove(root, key);
    }

    private AVLNode<K, V> doRemove(AVLNode<K, V> node, K key) {//删除
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {//左子树
            node.left = doRemove(node.left, key);//递归删除
        } else if (key.compareTo(node.key) > 0) {
            node.right = doRemove(node.right, key);//递归删除
        } else {//找到要删除的节点
            if (node.left == null && node.right == null) {//没有子树
                return null;
            } else if (node.left == null) {//只有右子树
                node = node.right;
            } else if (node.right == null) {//只有左子树
                node = node.left;
            } else {//有左右子树
                AVLNode<K, V> s = node.right;//找到右子树最小的节点
                while (s.left != null) {
                    s = s.left;
                }
                s.right = doRemove(node.right, s.key);//删除右子树最小的节点 链接右子树
                s.left = node.left;//链接左子树
                node = s;//替换
            }
        }
        updateHeight(node);
        return balance(node);
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> red) {//左旋转
        AVLNode<K, V> yellow = red.right;
        AVLNode<K, V> green = yellow.left;
        red.right = green;
        yellow.left = red;
        updateHeight(red);
        return yellow;
    }

    private AVLNode<K, V> leftRightRotate(AVLNode<K, V> node) {//左右旋转
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> red) {//右旋转
        AVLNode<K, V> yellow = red.left;
        AVLNode<K, V> green = yellow.right;
        red.left = green;
        yellow.right = red;
        updateHeight(red);
        return yellow;
    }

    private AVLNode<K, V> rightLeftRotate(AVLNode<K, V> node) {//右左旋转
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    private int getHeight(AVLNode<K, V> node) {//计算高度
        return node == null ? 0 : node.height;
    }

    private void updateHeight(AVLNode<K, V> node) {//更新高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private int getBalance(AVLNode<K, V> node) {//计算平衡因子
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode<K, V> balance(AVLNode<K, V> node) {
        if (node == null) {
            return null;
        }
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                node = rightRotate(node);
            } else {
                node = leftRightRotate(node);
            }
        } else if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                node = leftRotate(node);
            } else {
                node = rightLeftRotate(node);
            }
        }
        return node;
    }
    public void print() {
        System.out.println("levelOrder");
        levelOrder(root, list -> {
            for (List<V> level : list) {
                for (V v : level) {
                    System.out.print(v + " ");
                }
                System.out.println();
            }
            System.out.println();
        });

        System.out.println("inOrder");
        inOrder(root, node -> System.out.print(node.value + " "));
    }

    public V get(K key) {
        AVLNode<K, V> node = root;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (key.compareTo(node.key) > 0) {
                node = node.right;
            } else {
                return node.value;
            }
        }
        return null;
    }

    private void preOrder(AVLNode<K, V> node, Consumer<AVLNode<K, V>> consumer) {
        if (node == null) {
            return;
        }
        consumer.accept(node);
        preOrder(node.left, consumer);
        preOrder(node.right, consumer);
    }

    private void inOrder(AVLNode<K, V> node, Consumer<AVLNode<K, V>> consumer) {
        if (node == null) {
            return;
        }
        inOrder(node.left, consumer);
        consumer.accept(node);
        inOrder(node.right, consumer);
    }

    private void postOrder(AVLNode<K, V> node, Consumer<AVLNode<K, V>> consumer) {
        if (node == null) {
            return;
        }
        postOrder(node.left, consumer);
        postOrder(node.right, consumer);
        consumer.accept(node);
    }

    private void levelOrder(AVLNode<K, V> node, Consumer<List<List<V>>> consumer) {
        List<List<V>> result = new ArrayList<>();
        if (node == null) {
            return;
        }
        LinkedList<AVLNode<K, V>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            List<V> level = new ArrayList<>();
            while (curSize-- > 0) {
                AVLNode<K, V> cur = queue.poll();
                assert cur != null;
                level.add(cur.value);
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            result.add(level);
        }
        consumer.accept(result);
    }

}
