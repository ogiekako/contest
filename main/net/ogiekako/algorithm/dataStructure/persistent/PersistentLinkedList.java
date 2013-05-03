package net.ogiekako.algorithm.dataStructure.persistent;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/04/02
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class PersistentLinkedList<E> {
    private PersistentLinkedList(Entry<E> head) {
        this.head = head;
    }

    public PersistentLinkedList(E value) {
        this(new Entry<E>(value, PersistentLinkedList.<E>emptyList()));
    }

    public static <E> PersistentLinkedList<E> emptyList() {
        return new PersistentLinkedList<E>((Entry<E>) null);
    }

    public boolean isEmpty() {
        return head == null;
    }

    private static class Entry<E> {
        E value;
        PersistentLinkedList<E> next;

        private Entry(E value, PersistentLinkedList<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    public static <E> PersistentLinkedList<E> cons(E car, PersistentLinkedList<E> cdr) {
        if (car == null || cdr == null) throw new IllegalArgumentException();
        Entry<E> nHead = new Entry<E>(car, cdr);
        return new PersistentLinkedList<E>(nHead);
    }

    private Entry<E> head;

    public E car() {
        return head.value;
    }

    public PersistentLinkedList<E> cdr() {
        return head.next;
    }
}
