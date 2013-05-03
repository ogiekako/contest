package net.ogiekako.topcoder;

import net.ogiekako.algorithm.utils.Cast;

public class Encoder {
    String encode(int[] is){
        return encode(Cast.toLong(is));
    }
    String encode(long[] is){
        return encode(Cast.toString(is));
    }
    String encode(String[] ss){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ss.length; i++){
            if(i > 0)res.append(',');
            res.append(ss[i]);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        new Encoder().run();
    }

    private void run() {
        String[] ss = {"YYN","NNY","YYY"};
        String str = encode(ss);
        System.out.println(str);
    }
}
