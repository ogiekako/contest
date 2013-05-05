package net.ogiekako.algorithm.dataStructure.heap;
import net.ogiekako.algorithm.utils.ArrayUtils;
/**
 * Properties:                                                                             <br></br>
 * 1. The value of each node is smaller than or equal to each of its children              <br></br>
 * 2. The shape is a complete binary tree except the last level.
 * If the last level of the tree is not complete, the nodes are filled from left to right.
 * <p/>
 * Ref: <a href="http://en.wikipedia.org/wiki/Binary_heap">wikipedia:Binary heap</a>
 *
 * @param <V> The type of each element
 */
public class BinaryHeap<V extends Comparable<V>> implements Heap<V> {
    Object[] data;
    int size = 0;
    // root: 0,  children: i*2+1,i*2+2,  parent: (i-1)/2
    public BinaryHeap() {
        this(4);
    }

    public BinaryHeap(int initCapacity) {
        ensureCapacity(Math.max(1, Integer.highestOneBit(initCapacity - 1) * 2));
    }

    @SuppressWarnings("unchecked")
    void ensureCapacity(int n) {
        if (data == null) data = new Object[n];
        else {
            while (n > data.length) {
                Object[] newData = new Object[data.length * 2];
                System.arraycopy(data, 0, newData, 0, data.length);
                data = newData;
            }
        }
    }


    /**
     * O(log n)
     *
     * @return The minimum element
     */
    @SuppressWarnings("unchecked")
    public V poll() {
        if (size == 0) return null;
        V res = (V) data[0]; data[0] = data[size - 1]; data[--size] = null;
        // restore heap properties
        for (int pos = 0; ; ) {
            int min = pos;
            for (int c = pos * 2 + 1; c <= pos * 2 + 2; c++) {
                if (c < size && ((V) data[c]).compareTo((V) data[min]) < 0) min = c;
            }
            if (pos != min) {
                ArrayUtils.swap(data, pos, min);
                pos = min;
            } else break;
        }
        return res;
    }

    /**
     * O(log n)
     *
     * @param elem The element to be added.
     */
    @SuppressWarnings("unchecked")
    public void add(V elem) {
        ensureCapacity(size + 1);
        data[size++] = elem;
        // restore heap properties
        for (int pos = size - 1; pos > 0; ) {
            int parent = (pos - 1) / 2;
            if (((V) data[pos]).compareTo((V) data[parent]) < 0) {
                ArrayUtils.swap(data, pos, parent);
                pos = parent;
            } else break;
        }
    }

    public int size() {
        return size;
    }

    public void meld(Heap other) {
        throw new UnsupportedOperationException();
    }
}
