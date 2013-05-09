package net.ogiekako.algorithm.misc.embedding;

public class EmbeddingUtils {
    public static void embedIntArray(int[][] array, String name) {
        StringBuilder b = new StringBuilder();
        b.append("int[][] ").append(name).append("=");
        b.append('{');
        for (int[] a : array) {
            embedIntArray(b, a);
            b.append(",\n");
        }
        b.append('}');
        System.out.println(b);
    }

    private static void embedIntArray(StringBuilder b, int[] array) {
        b.append('{');
        for (int a : array) {
            b.append(a);
            b.append(',');
        }
        b.append('}');
    }

    public static void main(String[] args) {
        int[][] res = {{1, 2,},
                {3, 4,},
        };
        embedIntArray(res, "res");
    }
}
