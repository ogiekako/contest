package net.ogiekako.algorithm.dataStructure.intCollection;


public class IntQueue {
    private int[] data;
    private int from = 0, to = 0;
    private int size = 0;
    public IntQueue(int initCapacity) {
        data = new int[initCapacity];
    }
    public IntQueue() {
        this(4);
    }
    public void offer(int value) {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];
            for (int i = 0; i < size; i++) {
                int j = i + from;
                if (j >= size) j -= size;
                newData[i] = data[j];
            }
            from = 0;
            to = size;
            data = newData;
        }
        data[to++] = value;
        if (to >= data.length) to = 0;
        size++;
    }
    public int poll() {
        if (size == 0) throw new EmptyQueueException();
        int res = data[from++];
        if (from >= data.length) from = 0;
        size--;
        return res;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
}
