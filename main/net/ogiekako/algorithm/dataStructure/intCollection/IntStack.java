package net.ogiekako.algorithm.dataStructure.intCollection;

import java.util.EmptyStackException;

public class IntStack {
    private int[] is;
    int tail;
    public IntStack(int maxElement) {
        is = new int[maxElement];
        tail = 0;
    }
    public void push(int i) {
        if (tail == is.length) {
            int nextLength = (int) (is.length * 1.5 + 1);
            int[] js = new int[nextLength];
            System.arraycopy(is, 0, js, 0, is.length);
            is = js;
        }
        is[tail++] = i;
    }
    public int pop() {
        if (tail == 0) throw new EmptyStackException();
        return is[--tail];
    }

    public int peek() {
        if (tail == 0) throw new EmptyStackException();
        return is[tail - 1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }
}