package net.ogiekako.algorithm.utils;

public class StringUtils {

    public static boolean isLucky(String number) {
        for (char c : number.toCharArray()) {
            if (c != '4' && c != '7') return false;
        }
        return true;
    }

    public static int sum(String number) {
        int res = 0;
        for (char c : number.toCharArray()) res += c - '0';
        return res;
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static String concat(String[] ss) {
        StringBuilder b = new StringBuilder();
        for(String s:ss)b.append(s);
        return b.toString();
    }

    public static int[] toIntArray(String str, String delimiter) {
        String[] ss = str.split(delimiter);
        int[] res = new int[ss.length];
        for (int i = 0; i < ss.length; i++)res[i] = Integer.valueOf(ss[i]);
        return res;
    }

    public static String[] divideString(String str, int maxLength) {
        int arrayCount = (str.length()+maxLength-1)/maxLength;
        String[] res = new String[arrayCount];
        for (int i = 0; i < res.length; i++){
            res[i] = str.substring(i*maxLength, Math.min(str.length(),(i+1)*maxLength));
        }
        return res;
    }

    public static int[] encodeLowerCaseStringToIntArray(String str) {
        int[] res = new int[str.length()];
        for (int i = 0; i < str.length(); i++){
            res[i] = str.charAt(i)-'a';
        }
        return res;
    }

    /**
     * ( res>>i & 1 )== str[i] == one
     * @param str
     * @param one
     * @return
     */
    public static long toBitMask(String str, char one) {
        if(str.length()>64)throw new IllegalArgumentException();
        long res = 0;
        for(int i=0;i<str.length();i++)if(str.charAt(i)==one)res |= 1L<<i;
        return res;
    }

    public static final String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    public static final String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ACGT = "ACGT";
    public static final String digits = "0123456789";
}
