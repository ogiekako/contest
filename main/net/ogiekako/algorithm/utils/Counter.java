package net.ogiekako.algorithm.utils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/03/20
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public interface Counter<T> extends Map<T,Long>{
    void add(T key,long value);
}
