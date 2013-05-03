package net.ogiekako.algorithm.dataStructure;

import java.util.*;
/**
 * int[] をキーにする Map.
 * 実装は,HashMapを参考にした.
 * verified at SRM312Hard
 * @author ogiekako
 *
 */

class _{
    class Entry<V>{
    	int[] key;
    	V value;
    	private Entry<V> next;
    	private int hash;
    	private Entry(int[] key,V value,Entry<V> next,int hash){
    		this.key=key;
    		this.value=value;
    		this.next=next;
    		this.hash=hash;
    	}
    	int[] getKey() {
    		return key;
    	}
    	V getValue() {
    		return value;
    	}
    	public String toString(){
    		return String.format("{%s=%d}",Arrays.toString(key),value);
    	}
    }
    class ArrayMap<V>{
    	private final float LOAD_FACTOR = 0.75f;
    	private final int DEFAULT_INITIAL_CAPACITY = 64;
    	private Entry<V>[] table=new Entry[DEFAULT_INITIAL_CAPACITY];
    	private int size=0;
    	private int threshold = (int)(DEFAULT_INITIAL_CAPACITY * LOAD_FACTOR);
    	
    	HashSet<Entry<V>> entrySet(){
    		HashSet<Entry<V>>res=new HashSet<Entry<V>>();
    		for(int i=0;i<table.length;i++) {
    			Entry<V> e = table[i];
    			while(e!=null) {
    				res.add(e);
    				e=e.next;
    			}
    		}
    		return res;
    	}
    	
    	private int indexFor(int hash,int capacity) {
    		return hash&(capacity-1);
    	}
    	
    	private void transfer(Entry<V>[] newTable) {
    		Entry<V>[] src = table;
    		int newCapacity = newTable.length;
    		for(int i=0;i<src.length;i++) {
    			Entry<V> e = src[i];
    			while(e!=null) {
    				Entry<V> nxt = e.next;
    				int j=indexFor(e.hash,newCapacity);
    				e.next = newTable[j];
    				newTable[j]=e;
    				e=nxt;
    			}
    		}
    	}
       	
    	private void resize() {
    		int newCapacity = table.length*2;
    		Entry<V>[] newTable = new Entry[newCapacity];
    		transfer(newTable);
    		table=newTable;
    		threshold = (int)(newCapacity * LOAD_FACTOR);
    	}
    	
    	private void add(int id,int[] key,V val,int hash) {
    		table[id]=new Entry<V>(key,val,table[id],hash);
    		size++;
    		if(size>=threshold)resize();
    	}
    	
    	void put(int[] key,V val) {
    		int hash = Arrays.hashCode(key);
    		int id = indexFor(hash,table.length);
    		add(id,key,val,hash);
    	}
    	
    	V get(int[] key) {
    		if(key==null)throw new NullPointerException();
    		int hash = Arrays.hashCode(key);
    		for(Entry<V> e=table[indexFor(hash,table.length)];e!=null;e=e.next) {
    			if(e.hash == hash && Arrays.equals(e.key,key))return e.value;
    		}
    		return null;
    	}
    	
    	boolean containsKey(int[] key) {
    		return get(key)!=null;
    	}
    	
    	public String toString(){
    		return entrySet().toString();
    	}
    }
}