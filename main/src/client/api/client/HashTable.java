package client.api.client;

import client.reflection.Reflection;

/**
 * Created by Spencer on 10/15/2016.
 */
public class HashTable {

    private Object raw;
    private int index = 0;
    private Object current;

    public HashTable(Object raw) { this.raw = raw; }

    public Object[] getBuckets() {
        return (Object[]) Reflection.value("HashTable#getBuckets", raw);
    }

    public Object getCurrent() {
        return Reflection.value("HashTable#getCurrent", raw);
    }

    public Object getHead() {
        return Reflection.value("HashTable#getHead", raw);
    }

    public int getIndex() {
        return (int) Reflection.value("HashTable#getIndex", raw);
    }

    public int getSize() {
        return (int) Reflection.value("HashTable#getSize", raw);
    }

    public Object getNext() {
        final Object[] buckets = getBuckets();
        if (buckets == null) return null;

        if (index > 0 && current != buckets[index - 1]) {
            final Object node = current;
            if (current == null) {
                return null;
            }

            current = new Node(node).getPrevious();
            return node;
        }

        while (index < buckets.length) {
            final Object node = new Node(buckets[index++]).getPrevious();
            if (node != null) {
                if (buckets[index - 1] != node) {
                    current = new Node(node).getPrevious();
                    return node;
                }
            }
        }

        return null;
    }

    public void reset() {
        index = 0;
        current = null;
    }
}
