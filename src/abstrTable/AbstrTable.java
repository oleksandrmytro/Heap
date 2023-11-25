package abstrTable;

import static abstrTable.eTypProhl.HLOUBKA;
import static abstrTable.eTypProhl.SIRKA;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class AbstrTable<K extends Comparable<K>, V> {

    public static class Node<K, V> {

        public K key;
        public V value;
        Node<K, V> right;
        Node<K, V> left;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> root;

    public void zrus() {
        root = null;
    }

    public boolean jePrazdny() {
        return root == null;
    }

    public V najdi(K key) {
        Node<K, V> current = root;
        while (current != null) {
            int comp = key.compareTo(current.key);
            if (comp < 0) {
                current = current.left;
            } else if (comp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public void vloz(K key, V value) {
        if (najdi(key) != null) {
            throw new IllegalArgumentException("Záznam s klíčem " + key + " již existuje.");
        }
        root = vlozRecursive(root, key, value);
    }

    private Node<K, V> vlozRecursive(Node<K, V> curr, K key, V value) {
        if (curr == null) {
            curr = new Node<>(key, value);
        } else {
            int comp = key.compareTo(curr.key);
            if (comp < 0) {
                curr.left = vlozRecursive(curr.left, key, value);
            } else if (comp > 0) {
                curr.right = vlozRecursive(curr.right, key, value);
            }
        }
        return curr;
    }

    public V odeber(K key) {
        Node<K, V> toOdeber = root;
        if (toOdeber == null) {
            return null;
        }
        root = odeberRecursive(root, key);
        return toOdeber.value;
    }

    private Node<K, V> odeberRecursive(Node<K, V> curr, K key) {
        if (curr == null) {
            return null;
        }

        if (key == null) {
            throw new IllegalArgumentException("Key nemuze byt null");
        }

        int comp = key.compareTo(curr.key);
        if (comp < 0) {
            curr.left = odeberRecursive(curr.left, key);
        } else if (comp > 0) {
            curr.right = odeberRecursive(curr.right, key);
        } else {
            if (curr.left == null) {
                return curr.right;
            } else if (curr.right == null) {
                return curr.left;
            }

            Node<K, V> minNode = findMinNode(curr.right);

            curr.key = minNode.key;
            curr.value = minNode.value;
            curr.right = odeberRecursive(curr.right, minNode.key);
        }
        return curr;
    }

    private Node<K, V> findMinNode(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Iterator<V> vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case HLOUBKA:
                return new HloubkaProchazeni();
            case SIRKA:
                return new SirkaProchazeni();
            default:
                throw new IllegalArgumentException("Neznámý typ prohlídky: " + typ);
        }
    }

    private class HloubkaProchazeni implements Iterator<V> {

        private AbstrLIFO<Node<K, V>> lifo = new AbstrLIFO<>(); //Stack

        public HloubkaProchazeni() {
            Node<K, V> node = root;
            while (node != null) {
                lifo.vloz(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !lifo.jePrazdny();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node<K, V> node = lifo.odeber();
            V result = node.value;
            
            Node<K, V> nodeRight = node.right;
            while(nodeRight != null) {
                lifo.vloz(nodeRight);
                nodeRight = nodeRight.left;
            }
            return result;
        }
    }

    private class SirkaProchazeni implements Iterator<V> {

        private AbstrFIFO<Node<K, V>> fifo = new AbstrFIFO<>(); //Queue

        public SirkaProchazeni() {
            if (root != null) {
                fifo.vloz(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !fifo.jePrazdny();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<K, V> node = fifo.odeber();
            if (node.left != null) {
                fifo.vloz(node.left);
            }
            if (node.right != null) {
                fifo.vloz(node.right);
            }
            return node.value;
        }

    }

}
