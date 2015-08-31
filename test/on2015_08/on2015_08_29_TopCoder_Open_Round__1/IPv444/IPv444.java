package on2015_08.on2015_08_29_TopCoder_Open_Round__1.IPv444;



import java.util.Arrays;
import java.util.TreeSet;

public class IPv444 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long getMaximumMoney(String[] request, int[] price) {
        Entry[] es = new Entry[request.length];
        for (int i = 0; i < es.length; i++) {
            es[i] = new Entry(request[i], price[i]);
        }
        Arrays.sort(es);


        int[][] ids = new int[4][];
        for (int i = 0; i < 4; i++) {
            TreeSet<String> set = new TreeSet<String>();
            for (Entry e : es) {
                String[] ss = e.req.split("\\.");
                set.add(ss[i]);
            }
            ids[i] = new int[set.size()];
            int p = 0;
            for (String s : set) {
                ids[i][p++] = s.equals("*") ? -1 : Integer.valueOf(s);
            }
            Arrays.sort(ids[i]);
        }

        int[][][][] maxPrice = new int[ids[0].length][ids[1].length][ids[2].length][ids[3].length];

        boolean full = false;
        for (Entry e : es) {
            if (e.req.equals("*.*.*.*")) {
                if (full) continue;
                full = true;
            }
            String[] ss = e.req.split("\\.");
            int[][] ranges = new int[4][];
            for (int i = 0; i < 4; i++) {
                if (ss[i].equals("*")) {
                    ranges[i] = new int[ids[i].length];
                    for (int j = 0; j < ranges[i].length; j++) {
                        ranges[i][j] = j;
                    }
                } else {
                    ranges[i] = new int[]{Arrays.binarySearch(ids[i], Integer.valueOf(ss[i]))};
                }
            }
            for(int i1:ranges[0])for(int i2:ranges[1])for(int i3:ranges[2])for(int i4:ranges[3]){
                maxPrice[i1][i2][i3][i4] = Math.max(maxPrice[i1][i2][i3][i4], e.price);
            }
        }

        long res = 0;
        for(int i0=0;i0<ids[0].length;i0++) {
            long m0 = ids[0][i0] == -1 ? 1000 - ids[0].length + 1 : 1;
            for (int i1 = 0; i1 < ids[1].length; i1++) {
                long m1 = ids[1][i1] == -1 ? 1000 - ids[1].length + 1 : 1;
                for (int i2 = 0; i2 < ids[2].length; i2++) {
                    long m2 = ids[2][i2] == -1 ? 1000 - ids[2].length + 1 : 1;
                    for (int i3 = 0; i3 < ids[3].length; i3++) {
                        long m3 = ids[3][i3] == -1 ? 1000 - ids[3].length + 1 : 1;
                        res += m0 * m1 * m2 * m3 * maxPrice[i0][i1][i2][i3];
                    }
                }
            }
        }
        return res;
    }

    class Entry implements Comparable<Entry> {
        String req;
        int price;

        public Entry(String req, int price) {
            this.req = req;
            this.price = price;
        }

        @Override
        public int compareTo(Entry o) {
            return -Long.compare(price, o.price);
        }
    }
}
