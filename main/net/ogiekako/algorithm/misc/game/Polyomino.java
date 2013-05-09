package net.ogiekako.algorithm.misc.game;

import net.ogiekako.algorithm.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class Polyomino {
    public final int h, w;
    public final int[] mask;// 6 = 110 = ##.

    public Polyomino(int[] msk, int w) {
        this.h = msk.length;
        this.w = w;
        this.mask = msk;
    }

    /**
     * .      ##
     * ### -> #.
     * ..#    #.
     */
    public Polyomino rot90() {
        int[] nMsk = new int[w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((mask[i] >> j & 1) == 1) {
                    nMsk[j] |= 1 << h - 1 - i;
                }
            }
        }
        return new Polyomino(nMsk, h);
    }

    /**
     * ### -> ..#
     * ..#    ###
     */
    public Polyomino flipX() {
        int[] nMsk = new int[h];
        for (int i = 0; i < h; i++) {
            nMsk[h - 1 - i] = mask[i];
        }
        return new Polyomino(nMsk, w);
    }

    /**
     * ### -> ###
     * ..#    #..
     */
    public Polyomino flipY() {
        int[] nMsk = new int[h];
        for (int i = 0; i < h; i++) {
            nMsk[i] = IntegerUtils.reverse(mask[i], w);
        }
        return new Polyomino(nMsk, w);
    }

    public String[] toStringArray() {
        String[] res = new String[h];
        for (int i = 0; i < h; i++) {
            char[] cs = new char[w];
            for (int j = 0; j < w; j++) {
                if ((mask[i] >> j & 1) == 1) {
                    cs[w - 1 - j] = '#';
                } else {
                    cs[w - 1 - j] = '.';
                }
            }
            res[i] = new String(cs);
        }
        return res;
    }

    public String toString() {
        String[] array = toStringArray();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < h; i++) {
            if (i > 0) b.append('\n');
            b.append(array[i]);
        }
        return b.toString();
    }

    private static final Polyomino[][] polyominos = {
            {
                    convert(new String[]{"###"}, '#'),
                    convert(new String[]{"##", "#."}, '#')
            },

            {
                    convert(new String[]{"####"}, '#'),// I
                    convert(new String[]{"###", "#.."}, '#'),// L
                    convert(new String[]{"##", "##"}, '#'),// O
                    convert(new String[]{"##.", ".##"}, '#'),// S
                    convert(new String[]{"###", ".#."}, '#')// T
            },

            {
                    convert(new String[]{"##.", ".##", ".#."}, '#'),// F
                    convert(new String[]{"#####"}, '#'),// I
                    convert(new String[]{"####", "#..."}, '#'),// L
                    convert(new String[]{"###.", "..##"}, '#'),// N
                    convert(new String[]{"###", "##."}, '#'),// P
                    convert(new String[]{"###", ".#.", ".#."}, '#'),// T
                    convert(new String[]{"###", "#.#"}, '#'),// U
                    convert(new String[]{"###", "#..", "#.."}, '#'),// V
                    convert(new String[]{"##.", ".##", "..#"}, '#'),// W
                    convert(new String[]{".#.", "###", ".#."}, '#'),// X
                    convert(new String[]{"####", ".#.."}, '#'),// Y
                    convert(new String[]{"##.", ".#.", ".##"}, '#')// Z
            },
    };
    private static final String[] letters = {
            "IL",
            "ILOST",
            "FILNPTUVWXYZ"
    };

    public static Polyomino makeFromLetter(int cellCount, char letter) {
        if (cellCount < 3 || cellCount > 5) throw new IllegalArgumentException();
        int i = cellCount - 3;
        int p = letters[i].indexOf(letter);
        if (p < 0) throw new IllegalArgumentException();
        return polyominos[i][p];
    }

    public static Polyomino convert(String[] ss, char cellLetter) {
        int h = ss.length;
        int w = ss[0].length();
        int[] msk = new int[h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (ss[i].charAt(j) == cellLetter) {
                    msk[i] |= 1 << w - 1 - j;
                }
            }
        }
        return new Polyomino(msk, w);
    }

    public Polyomino[] generateAll() {
        Polyomino p = this;
        List<Polyomino> list = new ArrayList<Polyomino>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (!list.contains(p))
                    list.add(p);
                p = p.rot90();
            }
            p = p.flipX();
        }
        return list.toArray(new Polyomino[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polyomino polyomino = (Polyomino) o;

        if (w != polyomino.w) return false;
        if (!Arrays.equals(mask, polyomino.mask)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = w;
        result = 31 * result + Arrays.hashCode(mask);
        return result;
    }

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static List<Polyomino> generateAll(int n) {
        if (n == 1) {
            List<Polyomino> list = new ArrayList<Polyomino>();
            list.add(new Polyomino(new int[]{1}, 1));
            return list;
        } else {
            List<Polyomino> nMinus1 = generateAll(n - 1);
            HashSet<Polyomino> set = new HashSet<Polyomino>();
            List<Polyomino> res = new ArrayList<Polyomino>();
            for (Polyomino poly : nMinus1) {
                int h = poly.h;
                int w = poly.w;
                int[] nMask = new int[h + 2];
                for (int i = 0; i < h; i++) nMask[i + 1] = poly.mask[i] << 1;
                h += 2; w += 2;
                for (int i = 0; i < h; i++)
                    for (int j = 0; j < w; j++)
                        if ((nMask[i] >> j & 1) == 1) {
                            for (int d = 0; d < 4; d++) {
                                int nx = i + dx[d];
                                int ny = j + dy[d];
                                if ((nMask[nx] >> ny & 1) == 1) continue;
                                nMask[nx] ^= 1 << ny;
                                Polyomino nPoly = new Polyomino(nMask, w);
                                nPoly = nPoly.trimed();
                                boolean same = false;
                                for (Polyomino p : nPoly.generateAll()) {
                                    if (set.contains(p)) {
                                        same = true; break;
                                    }
                                }
                                if (!same) {
                                    res.add(nPoly);
                                    set.add(nPoly);
                                }
                                nMask[nx] ^= 1 << ny;
                            }
                        }
            }
            return res;
        }
    }

    public boolean isSame(Polyomino poly) {
        for (Polyomino p : poly.generateAll()) {
            if (p.equals(this)) return true;
        }
        return false;
    }

    private Polyomino trimed() {
        int x1 = 0;
        while (mask[x1] == 0) x1++;
        int x2 = mask.length;
        while (mask[x2 - 1] == 0) x2--;
        int y1;
        for (y1 = 0; ; y1++) {
            boolean ex = false;
            for (int msk : mask) if ((msk >> y1 & 1) != 0) ex = true;
            if (ex) break;
        }
        int y2;
        for (y2 = w; ; y2--) {
            boolean ex = false;
            for (int msk : mask) if ((msk >> y2 - 1 & 1) != 0) ex = true;
            if (ex) break;
        }
        int nW = y2 - y1;
        int[] nMask = new int[x2 - x1];
        for (int i = x1; i < x2; i++) {
            nMask[i - x1] = mask[i] >> y1;
        }
        return new Polyomino(nMask, nW);
    }
}
