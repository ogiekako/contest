package net.ogiekako.algorithm.dataStructure.tree;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplayTreeListTest {
    Random rnd = new Random(124124124L);

    @Test
    public void testRMQ() {
        test(new Generator<Integer>() {
            public SplayTreeList<Integer> generate() {
                return new SplayTreeList.Min();
            }

            public Integer random(Random rnd) {
                return rnd.nextInt(201) - 100;
            }
        });
    }

    @Test
    public void testSum() {
        test(new Generator<Integer>() {
            public SplayTreeList<Integer> generate() {
                return new SplayTreeList.Sum();
            }

            public Integer random(Random rnd) {
                return rnd.nextInt(201) - 100;
            }
        });
    }

    enum Type {
        SET, CUT, APPEND, GET, QUERY
    }

    static void log(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }

    private <E> void test(Generator<E> gen) {
        testValidity(gen);
        testSpeed(gen);
    }

    private <E> void testSpeed(Generator<E> gen) {
        Type[] types = Type.values();
        int n = 100000;
        int m = 100000;
        double limit = 3.0;
        for (int test = 0; test < 3; test++) {
            long start = System.currentTimeMillis();
            List<SplayTreeList<E>> lists = new ArrayList<SplayTreeList<E>>();
            lists.add(gen.generate());
            for (int i = 0; i < n; i++) {
                E value = gen.random(rnd);
                lists.get(0).add(value);
            }

            for (int iter = 0; iter < m; iter++) {
                int numList = lists.size();
                int target = rnd.nextInt(numList);
                int target2 = rnd.nextInt(numList);
                SplayTreeList<E> list = lists.get(target);
                SplayTreeList<E> list2 = lists.get(target2);
                int s = rnd.nextInt(list.size());
                int t = rnd.nextInt(list.size());
                if (s > t) {int tmp = s; s = t; t = tmp;}
                t++;
                E value = gen.random(rnd);
                Type type = types[rnd.nextInt(types.length)];
                log("type", type);
                switch (type) {
                    case SET:
                        list.set(s, value);
                        break;
                    case CUT:
                        SplayTreeList<E> other = list.cut(s);
                        if (list.size() == 0) lists.remove(list);
                        if (other.size() > 0) lists.add(other);
                        break;
                    case APPEND:
                        log(s, t);
                        if (list != list2) {
                            list.append(list2);
                            lists.remove(list2);
                        } break;
                    case GET:
                        E res = list.get(s);
                        break;
                    case QUERY:
                        int prevSize = list.size();
                        log(s, t);
                        res = list.query(s, t);
                        Assert.assertEquals(prevSize, list.size());
                        break;
                }
            }
            long time = System.currentTimeMillis() - start;
            Assert.assertTrue(time < limit * 1000);
        }
    }

    private <E> void testValidity(Generator<E> gen) {
        Type[] types = Type.values();
        for (int o = 0; o < 100; o++)
            for (int n : new int[]{1, 4, 5, 10, 50}) {
                log("n", n);
                List<List<E>> eLists = new ArrayList<List<E>>();
                List<SplayTreeList<E>> lists = new ArrayList<SplayTreeList<E>>();
                eLists.add(new ArrayList<E>());
                lists.add(gen.generate());
                for (int i = 0; i < n; i++) {
                    E value = gen.random(rnd);
                    lists.get(0).add(value);
                    eLists.get(0).add(value);
                }

                for (int iter = 0; iter < 1000; iter++) {
                    log("iter", iter);
                    log("eLists", eLists);
                    int numList = eLists.size();
                    int target = rnd.nextInt(numList);
                    int target2 = rnd.nextInt(numList);
                    List<E> eList = eLists.get(target);
                    List<E> eList2 = eLists.get(target2);
                    SplayTreeList<E> list = lists.get(target);
                    SplayTreeList<E> list2 = lists.get(target2);
                    int s = rnd.nextInt(list.size());
                    int t = rnd.nextInt(list.size());
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    E value = gen.random(rnd);
                    Type type = types[rnd.nextInt(types.length)];
                    log("type", type);
                    switch (type) {
                        case SET:
                            eList.set(s, value);
                            list.set(s, value);
                            break;
                        case CUT:
                            List<E> eOther = cut(eList, s);
                            if (eList.size() == 0) eLists.remove(eList);
                            if (eOther.size() > 0) eLists.add(eOther);
                            SplayTreeList<E> other = list.cut(s);
                            if (list.size() == 0) lists.remove(list);
                            if (other.size() > 0) lists.add(other);
                            break;
                        case APPEND:
                            log(s, t);
                            if (list != list2) {
                                list.append(list2);
                                eList.addAll(eList2);
                                lists.remove(list2);
                                eLists.remove(target2);
                            } break;
                        case GET:
                            E res = list.get(s);
                            E exp = eList.get(s);
                            Assert.assertEquals(exp, res);
                            break;
                        case QUERY:
                            int prevSize = list.size();
                            log(s, t);
                            res = list.query(s, t);
                            Assert.assertEquals(prevSize, list.size());
                            exp = list.identity();
                            for (int i = s; i < t; i++) exp = list.join(exp, eList.get(i));
                            Assert.assertEquals(exp, res);
                            break;
                    }
                    Assert.assertEquals(eLists.size(), lists.size());
                    for (int i = 0; i < eLists.size(); i++) {
                        Assert.assertEquals(i + "", eLists.get(i).size(), lists.get(i).size());
                        for (int j = 0; j < eLists.get(i).size(); j++) {
                            Assert.assertEquals(eLists.get(i).get(j), lists.get(i).get(j));
                        }
                    }
                }
            }
    }

    private <E> List<E> cut(List<E> list, int from) {
        List<E> res = new ArrayList<E>();
        while (list.size() > from) res.add(list.remove(from));
        return res;
    }

    interface Generator<E> {
        SplayTreeList<E> generate();

        E random(Random rnd);
    }
}
