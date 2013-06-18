package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;

import junit.framework.Assert;
import net.ogiekako.algorithm.dataStructure.tree.QueryableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplayTreeListTest {
    Random rnd = new Random(124124124L);
    static boolean LOG = false;

    @Test(timeout = 2000)
    public void testRMQ() {
        test(new Generator<Integer>() {
            public SplayTreeList<Integer> generate() {
                return new SplayTreeList.Min();
            }
            public Integer identity() {
                return generate().identity();
            }
            public Integer join(Integer left, Integer right) {
                return generate().join(left, right);
            }

            public Integer random(Random rnd) {
                return rnd.nextInt(201) - 100;
            }
        });
    }

    @Test(timeout = 2000)
    public void testSum() {
        test(new Generator<Integer>() {
            public SplayTreeList<Integer> generate() {
                return new SplayTreeList.Sum();
            }
            public Integer identity() {
                return generate().identity();
            }
            public Integer join(Integer left, Integer right) {
                return generate().join(left, right);
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
        if (LOG)
            System.out.println(Arrays.deepToString(os));
    }

    private <E> void test(Generator<E> gen) {
        test(gen, true, 10, new int[]{1, 4, 5, 10, 50}, 1000);
        test(gen, false, 3, new int[]{100000}, 100000);
    }

    private <E> void test(Generator<E> gen, boolean check, int iteration, int[] sizes, int numQuery) {
        Type[] types = Type.values();
        for (int o = 0; o < iteration; o++)
            for (int size : sizes) {
                log("size", size);
                List<List<E>> eLists = new ArrayList<List<E>>();
                List<QueryableList<E>> lists = new ArrayList<QueryableList<E>>();
                eLists.add(new ArrayList<E>());
                lists.add(gen.generate());
                for (int i = 0; i < size; i++) {
                    E value = gen.random(rnd);
                    lists.get(0).add(value);
                    eLists.get(0).add(value);
                }

                for (int query = 0; query < numQuery; query++) {
                    log("query", query);
                    log("eLists", eLists);
                    int numList = eLists.size();
                    int target = rnd.nextInt(numList);
                    int target2 = rnd.nextInt(numList);
                    QueryableList<E> list = lists.get(target);
                    QueryableList<E> list2 = lists.get(target2);
                    int s = rnd.nextInt(list.size());
                    int t = rnd.nextInt(list.size());
                    if (s > t) {int tmp = s; s = t; t = tmp;}
                    t++;
                    E value = gen.random(rnd);
                    Type type = types[rnd.nextInt(types.length)];
                    log("type", type);
                    List<E> eList = eLists.get(target);
                    List<E> eList2 = eLists.get(target2);
                    switch (type) {
                        case SET:
                            if (check)
                                eList.set(s, value);
                            list.set(s, value);
                            break;
                        case CUT:
                            if (check) {
                                List<E> eOther = cut(eList, s);
                                if (eList.size() == 0) eLists.remove(eList);
                                if (eOther.size() > 0) eLists.add(eOther);
                            }
                            QueryableList<E> other = (QueryableList<E>) list.cut(s);
                            if (list.size() == 0) lists.remove(list);
                            if (other.size() > 0) lists.add(other);
                            break;
                        case APPEND:
                            log(s, t);
                            if (list != list2) {
                                if (check) {
                                    eList.addAll(eList2);
                                    eLists.remove(target2);
                                }
                                list.append(list2);
                                lists.remove(list2);
                            } break;
                        case GET:
                            E res = list.get(s);
                            if (check) {
                                E exp = eList.get(s);
                                Assert.assertEquals(exp, res);
                            }
                            break;
                        case QUERY:
                            int prevSize = list.size();
                            log(s, t);
                            res = list.query(s, t);
                            Assert.assertEquals(prevSize, list.size());
                            if (check) {
                                E exp = gen.identity();
                                for (int i = s; i < t; i++) exp = gen.join(exp, eList.get(i));
                                Assert.assertEquals(exp, res);
                            }
                            break;
                    }
                    if (check) {
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
    }

    private <E> List<E> cut(List<E> list, int from) {
        List<E> res = new ArrayList<E>();
        while (list.size() > from) res.add(list.remove(from));
        return res;
    }

    interface Generator<E> {
        QueryableList<E> generate();
        E identity();
        E join(E left, E right);
        E random(Random rnd);
    }
}
