package net.ogiekako.algorithm.dataStructure.intCollection;

public class IntArrayList {
    int[] data;
    int size;

    public IntArrayList() {
        this(4);
    }

    public IntArrayList(int initCapacity) {
        initCapacity = Math.max(1, initCapacity);
        data = new int[initCapacity];
        size = 0;
    }

    public void add(int value) {
        if (size >= data.length) {
            int[] nData = new int[data.length * 2];
            System.arraycopy(data, 0, nData, 0, data.length);
            data = nData;
        }
        data[size++] = value;
    }

    public int get(int i) {
        if (i >= size) throw new IndexOutOfBoundsException(String.format("size = %d, i = %d", size, i));
        return data[i];
    }

    public int size() {
        return size;
    }

    public IntIterator iterator() {
        return new IntIterator() {
            int i = 0;
            public boolean hasNext() {
                return i < size;
            }

            public int next() {
                return get(i++);
            }
            public int peek() {
                return get(i);
            }
        };
    }
}
