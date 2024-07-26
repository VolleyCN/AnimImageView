package com.moses.lib.treenode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BSTTree<K extends Comparable<K>, V> {

    public static class BSTNode<K, V> {
        V value;
        K key;
        BSTNode<K, V> left;
        BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode<K, V> root;
    private int size;
    private final int capacity;


    public BSTTree() {
        this(Integer.MAX_VALUE);
    }

    public BSTTree(BSTNode<K, V> root) {
        this.root = root;
        this.capacity = Integer.MAX_VALUE;
    }

    public BSTTree(int capacity) {
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(K key, V value) {
        if (isFull()) {
            throw new RuntimeException("tree is full");
        }
        doPut2(root, key, value);
    }

    private void doPut2(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            root = new BSTNode<>(key, value);
            size++;
            return;
        }
        BSTNode<K, V> cur = node;
        BSTNode<K, V> parent = null;
        while (cur != null) {
            parent = cur;//父节点
            if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else {
                cur.value = value;
                return;
            }
        }
        if (key.compareTo(parent.key) < 0) {
            parent.left = new BSTNode<>(key, value);
        } else {
            parent.right = new BSTNode<>(key, value);
        }
        size++;
    }

    private void doPut(BSTNode<K, V> node, K key, V value) {
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) {
                node.left = new BSTNode<>(key, value);
            } else {
                doPut(node.left, key, value);
            }
        } else if (key.compareTo(node.key) > 0) {
            if (node.right == null) {
                node.right = new BSTNode<>(key, value);
            } else {
                doPut(node.right, key, value);
            }
        } else {
            node.value = value;
        }
    }

    public V min() {
        return doMin(root);
    }

    private V doMin(BSTNode<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node.value;
        }
        return doMin(node.left);
    }

    public V max() {
        return doMax(root);
    }

    private V doMax(BSTNode<K, V> node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node.value;
        }
        return doMax(node.right);
    }

    public V get(K key) {
        return doGet(root, key);
    }


    private V doGet(BSTNode<K, V> node, K key) {
        if (node == null || key == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return doGet(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return doGet(node.right, key);
        } else {
            return node.value;
        }
    }

    private V doGet2(BSTNode<K, V> node, K key) {
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

    public V remove(K key) {
        return doRemove(root, key);
    }

    public V remove2(K key) {
        V v = doGet(root, key);
        if (v == null) {
            return null;
        }
        root = doRemove2(root, key);
        return v;
    }

    private BSTNode<K, V> doRemove2(BSTNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = doRemove2(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = doRemove2(node.right, key);
            return node;
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                BSTNode<K, V> s = node.right;
                while (s.left != null) {
                    s = s.left;
                }
                s.right = doRemove2(node.right, s.key);
                s.left = node.left;
                size--;
                return s;
            }
        }
    }

    private V doRemove(BSTNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        BSTNode<K, V> p = root;
        BSTNode<K, V> parent = null;
        while (p != null) {
            if (key.compareTo(p.key) < 0) {
                parent = p;
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                parent = p;
                p = p.right;
            } else {
                break;
            }
        }
        if (p == null) {
            return null;
        }

        if (p.right == null) {//右子树为空
            shift(parent, p, p.left);
        } else if (p.left == null) {//左子树为空
            shift(parent, p, p.right);
        } else {
            if (p.left.right == null) {//左子树只有一个节点
                shift(parent, p, p.left);
                p.left.right = p.right;
            } else {//左子树有多个节点
                //找到右子树最小节点
                BSTNode<K, V> s = p.right;
                BSTNode<K, V> sParent = p;
                while (s.left != null) {
                    sParent = s;
                    s = s.left;
                }
                if (sParent != p) {
                    shift(sParent, s, s.right);//不可能有左子树
                    s.right = p.right;
                }
                shift(parent, p, s);
                s.left = p.left;
                //删除右子树最小节点
            }
        }
        size--;
        return p.value;
    }

    public V successor(K key) {
        return doSuccessor(root, key);
    }

    //找后继
    private V doSuccessor(BSTNode<K, V> node, K key) {
        if (node == null || key == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return doSuccessor(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return doSuccessor(node.right, key);
        } else {
            if (node.right != null) {
                return doMin(node.right);
            }
            BSTNode<K, V> parent = null;
            BSTNode<K, V> cur = node;
            while (cur != null) {
                parent = cur;
                cur = cur.left;
            }
            return parent.value;
        }
    }

    private V doSuccessor2(BSTNode<K, V> node, K key) {
        if (node == null || key == null) {
            return null;
        }
        BSTNode<K, V> cur = node;
        BSTNode<K, V> parent = null;
        while (cur != null) {
            if (key.compareTo(cur.key) < 0) {
                parent = cur;
                cur = cur.left;
            } else if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else {
                if (cur.right != null) {
                    return doMin(cur.right);
                }
                return parent == null ? null : parent.value;
            }
        }
        return null;
    }

    public V predecessor(K key) {
        return doPredecessor(root, key);
    }

    //找前驱
    private V doPredecessor(BSTNode<K, V> root, K key) {
        if (root == null || key == null) {
            return null;
        }
        if (key.compareTo(root.key) < 0) {
            return doPredecessor(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return doPredecessor(root.right, key);
        } else {
            if (root.left != null) {
                return doMax(root.left);
            }
            BSTNode<K, V> parent = null;
            BSTNode<K, V> cur = root;
            while (cur != null) {
                parent = cur;
                cur = cur.right;
            }
            return parent.value;
        }
    }

    /**
     *
     */
    private V doPredecessor2(BSTNode<K, V> node, K key) {
        if (node == null || key == null) {
            return null;
        }
        BSTNode<K, V> parent = null;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (key.compareTo(node.key) > 0) {
                node = node.right;
                parent = node;
            } else {
                if (node.left != null) {
                    return doMax(node.left);
                } else {
                    return parent == null ? null : parent.value;
                }
            }
        }
        return null;
    }


    /**
     * @param parent     删除节点的父节点
     * @param deleteNode 删除节点
     * @param child      删除节点的子节点
     */
    private void shift(BSTNode<K, V> parent, BSTNode<K, V> deleteNode, BSTNode<K, V> child) {
        if (parent == null) {
            root = child;
        } else if (parent.left == deleteNode) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }


    public void print() {
//        System.out.println("preOrder");
//        preOrder(root, System.out::println);
//        System.out.println("inOrder");
//        inOrder(root, System.out::println);
//        System.out.println("postOrder");
//        postOrder(root, System.out::println);
        System.out.println("reverseInOrder");
        reverseInOrder(root, System.out::println);
        System.out.println("levelOrder");
        levelOrder(root, lists -> {
            for (List<V> list : lists) {
                for (V v : list) {
                    System.out.print(v + " ");
                }
                System.out.println();
            }
        });
    }

    public void levelOrder(BSTNode<K, V> node, Consumer<List<List<V>>> consumer) {
        List<List<V>> result = new ArrayList<>();
        LinkedList<BSTNode<K, V>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            List<V> level = new ArrayList<>();
            while (curSize-- > 0) {
                BSTNode<K, V> cur = queue.poll();
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

    private void preOrder(BSTNode<K, V> node, Consumer<V> consumer) {
        if (node == null) {
            return;
        }
        consumer.accept(node.value);
        inOrder(node.left, consumer);
        inOrder(node.right, consumer);
    }

    private void postOrder(BSTNode<K, V> node, Consumer<V> consumer) {
        if (node == null) {
            return;
        }
        inOrder(node.left, consumer);
        inOrder(node.right, consumer);
        consumer.accept(node.value);
    }

    private void inOrder(BSTNode<K, V> node, Consumer<V> consumer) {
        if (node == null) {
            return;
        }
        inOrder(node.left, consumer);
        consumer.accept(node.value);
        inOrder(node.right, consumer);
    }

    private void reverseInOrder(BSTNode<K, V> node, Consumer<V> consumer){
        if (node == null) {
            return;
        }
        reverseInOrder(node.right, consumer);
        consumer.accept(node.value);
        reverseInOrder(node.left, consumer);
    }

}
