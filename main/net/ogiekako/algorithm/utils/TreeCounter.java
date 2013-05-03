package net.ogiekako.algorithm.utils;

import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/20
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class TreeCounter<T> extends TreeMap<T,Long> implements Counter<T> {
    public void add(T key, long value) {
        Long cur = get(key);
        if(cur==null)put(key,value);
        else put(key,cur+value);
    }
}
