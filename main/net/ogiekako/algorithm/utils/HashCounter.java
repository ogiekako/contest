package net.ogiekako.algorithm.utils;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/20
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class HashCounter<T> extends HashMap<T,Long> implements Counter<T> {
    public void add(T key,long value){
        Long current = get(key);
        if(current==null)put(key,value);
        else put(key,current + value);
    }
}
