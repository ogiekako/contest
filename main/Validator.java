package tmp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

import static tmp.AssertionUtils.*;

public class Validator {
    public static void main(String[] args) {
        try {
            new Validator().validate();
            System.out.println("ok");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            System.exit(1);
        }
    }

    StrictScanner in;

    {
//        try {
//            in = new StrictScanner(new File("module/src/tmp/10_random00.in"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        in = new StrictScanner(System.in);
    }

    int n, m;
    char[][][] glyphs;
    char[][][] revs;
    int maxHeight;
    char[] letters;
    char[][][] boards;
    final int N = 26;
    final int M = 10;
    final int H_GLYPH = 15, W_GLYPH = 15;
    final int H_BOARD = 100, W_BOARD = 1000;
    final char WHITE = '.', BLACK = '*';

    public void validate() {
        read();
        check();
    }

    private void check() {
        log("checkRange");
        checkRange();
        log("checkGlyph");
        checkGlyphAllDifferent();
        checkGlyphNoWhiteLine();
        log("checkBoards");
        checkBoards();
    }

    private void log(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    private void checkBoards() {
        for (char[][] board : boards) checkBoard(board);
    }

    private void checkBoard(char[][] board) {
        int h = board.length, w = board[0].length;
        assertFalse(isEmpty(board, 0, 0, h, w));
        checkBoard(board, 0, 0, h, w);
    }

    private void checkBoard(char[][] board, int x0, int y0, int x1, int y1) {
        log("checkBoard", x0,y0,x1,y1);
        int mask = 3;
        while (y0 < y1) {
            int y = y0 + 1;
            while (y<y1 && !isEmpty(board, x0, y-1, x1, y)) y++;
            int p = single(board, x0, y0, x1, y);
            if (p > 0) mask &= p;
            y0 = y;
        }
        assertTrue(mask > 0);
    }

    private int single(char[][] board, int x0, int y0, int x1, int y1) {
        log("single",x0,y0,x1,y1);
        while (x0 < x1 && isEmpty(board, x0, y0, x0 + 1, y1)) x0++;
        if (x0 == x1) return 0;
        while (isEmpty(board, x1 - 1, y0, x1, y1)) x1--;
        while (isEmpty(board, x0, y0, x1, y0 + 1)) y0++;
        while (isEmpty(board, x0, y1 - 1, x1, y1)) y1--;
        int h = x1 - x0, w = y1 - y0;
        if (h > maxHeight) {
            assertTrue(h >= 3 && w >= 3);
            checkGrid(board, x0, y0, x1, y1, BLACK);
            checkGrid(board, x0 + 1, y0 + 1, x1 - 1, y1 - 1, WHITE);
            checkBoard(board, x0 + 2, y0 + 2, x1 - 2, y1 - 2);
            return 0;
        } else {
            char[][] part = subArray(board, x0, y0, x1, y1);
            int mask = 0;
            for (char[][] glyph : glyphs) {
                if (Arrays.deepEquals(part, glyph)) mask |= 1;
            }
            for(char[][] rev : revs){
                if(Arrays.deepEquals(part, rev)) mask |= 2;
            }
            assertTrue(mask > 0);
            return mask;
        }
    }

    private char[][] subArray(char[][] board, int x0, int y0, int x1, int y1) {
        char[][] res = new char[x1 - x0][y1 - y0];
        for (int x = x0; x < x1; x++)
            for (int y = y0; y < y1; y++) {
                res[x - x0][y - y0] = board[x][y];
            }
        return res;
    }

    private char[][] reversed(char[][] cs) {
        char[][] res = new char[cs.length][];
        for (int i = 0; i < cs.length; i++)
            res[i] = new StringBuilder(String.valueOf(cs[i])).reverse().toString().toCharArray();
        return res;
    }

    private void checkGrid(char[][] board, int x0, int y0, int x1, int y1, char color) {
        log("checkGrid",x0,y0,x1,y1,color);
        for (int x = x0; x < x1; x++) assertTrue(board[x][y0] == color);
        for (int x = x0; x < x1; x++) assertTrue(board[x][y1 - 1] == color);
        for (int y = y0; y < y1; y++) assertTrue(board[x0][y] == color);
        for (int y = y0; y < y1; y++) assertTrue(board[x1 - 1][y] == color);
    }

    private boolean isEmpty(char[][] board, int x0, int y0, int x1, int y1) {
        for (int x = x0; x < x1; x++) for (int y = y0; y < y1; y++) if (board[x][y] == BLACK) return false;
        return true;
    }

    private void checkGlyphNoWhiteLine() {
        for (char[][] glyph : glyphs) {
            // while row is OK.
//            for (char[] row : glyph) assertTrue(String.valueOf(row).contains("" + BLACK));
            for (char[] col : transposed(glyph)) assertTrue(String.valueOf(col).contains("" + BLACK));
        }
    }

    private char[][] transposed(char[][] cs) {
        char[][] res = new char[cs[0].length][cs.length];
        for (int i = 0; i < res.length; i++) for (int j = 0; j < res[0].length; j++) res[i][j] = cs[j][i];
        return res;
    }

    private void checkGlyphAllDifferent() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                assertFalse(Arrays.deepEquals(glyphs[i], glyphs[j]));
            }
    }

    private void checkRange() {
        assertInRange(n, 1, N);
        assertInRange(m, 1, M);
        assertAllDifferent(letters);
        assertInCharList(String.valueOf(letters), AssertionUtils.SMALL_ALPHABETS);
        for (char[][] glyph : glyphs) {
            int h = glyph.length, w = glyph[0].length;
            assertInRange(h, 1, H_GLYPH);
            assertInRange(w, 1, W_GLYPH);
            for (char[] row : glyph) assertInCharList(String.valueOf(row), "" + WHITE + BLACK);
        }
        for (char[][] board : boards) {
            int h = board.length, w = board[0].length;
            assertInRange(h, 1, H_BOARD);
            assertInRange(w, 1, W_BOARD);
            for (char[] row : board) assertInCharList(String.valueOf(row), "" + WHITE + BLACK);
        }
    }

    private void read() {
        n = in.nextInt(); in.nextWhiteSpace(); m = in.nextInt(); in.nextLineBreak();
        letters = new char[n];
        glyphs = new char[n][][];
        revs = new char[n][][];
        maxHeight = 0;
        for (int i = 0; i < n; i++) {
            letters[i] = in.nextChar(); in.nextWhiteSpace();
            int h = in.nextInt(); in.nextWhiteSpace();
            maxHeight = Math.max(maxHeight, h);
            int w = in.nextInt(); in.nextLineBreak();
            glyphs[i] = new char[h][w];
            for (int x = 0; x < h; x++) {
                for (int y = 0; y < w; y++) {
                    glyphs[i][x][y] = in.nextChar();
                }
                in.nextLineBreak();
            }
            revs[i] = reversed(glyphs[i]);
        }
        boards = new char[m][][];
        for (int i = 0; i < m; i++) {
            int h = in.nextInt(); in.nextWhiteSpace();
            int w = in.nextInt(); in.nextLineBreak();
            boards[i] = new char[h][w];
            for (int x = 0; x < h; x++) {
                for (int y = 0; y < w; y++) {
                    boards[i][x][y] = in.nextChar();
                }
                in.nextLineBreak();
            }
        }
        in.assertIsEof();
    }
}


class StrictScanner {

    private final static String EOL = "\n";

    private final static Pattern JWS = Pattern.compile("\\p{javaWhitespace}");
    private final static Pattern NORMAL_INTEGER = Pattern.compile("0|-?[1-9][0-9]*");
    private final static Pattern NORMAL_FLOATING_POINT = Pattern.compile("-?(0|[1-9][0-9]*)(\\.[0-9]+)?");

    private final Scanner sc;

    public StrictScanner(InputStream is) {
        sc = new Scanner(is);
    }

    public StrictScanner(File file) throws FileNotFoundException {
        sc = new Scanner(file);
    }

    public String nextWhiteSpace() {
        assertIsNotEof();
        sc.useDelimiter("");
        if (!sc.hasNext(" ")) {
            throw new InputMismatchException();
        }
        return sc.next();
    }

    @Deprecated
    public String readWhiteSpace() {
        return nextWhiteSpace();
    }

    public char nextChar() {
        assertIsNotEof();
        sc.useDelimiter("");
        return sc.next().charAt(0);
    }

    @Deprecated
    public char readChar() {
        return nextChar();
    }

    public String nextWord() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        return sc.next();
    }

    @Deprecated
    public String readWord() {
        return nextWord();
    }

    public String nextLine() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        assertIsNotEof();
        sc.useDelimiter(EOL);
        String s = sc.next();
        nextLineBreak();
        return s;
    }

    @Deprecated
    public String readLine() {
        return nextLine();
    }

    public int nextInt() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextInt()) {
            throw new InputMismatchException();
        }
        String intString = sc.next();
        if (!isNormalIntegerString(intString)) {
            throw new InputMismatchException();
        }
        return Integer.parseInt(intString);
    }

    @Deprecated
    public int readInt() {
        return nextInt();
    }

    public long nextLong() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextLong()) {
            throw new InputMismatchException();
        }
        String longString = sc.next();
        if (!isNormalIntegerString(longString)) {
            throw new InputMismatchException();
        }
        return Long.parseLong(longString);
    }

    @Deprecated
    public long readLong() {
        return nextLong();
    }

    public double nextDouble() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextDouble()) {
            throw new InputMismatchException();
        }
        String doubleString = sc.next();
        if (!isNormalFloatingPoint(doubleString)) {
            throw new InputMismatchException();
        }
        return Double.parseDouble(doubleString);
    }

    @Deprecated
    public double readDouble() {
        return nextDouble();
    }

    public double nextDouble(int minNumberDecimals, int maxNumberDecimals) {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextDouble()) {
            throw new InputMismatchException();
        }
        String doubleString = sc.next();
        if (!isNormalFloatingPoint(doubleString)) {
            throw new InputMismatchException();
        }
        int numberDecimals = numberDecimals(doubleString);
        if (numberDecimals < minNumberDecimals || maxNumberDecimals < numberDecimals) {
            throw new InputMismatchException();
        }
        return Double.parseDouble(doubleString);
    }

    @Deprecated
    public double readDouble(int minNumberDecimals, int maxNumberDecimals) {
        return nextDouble(minNumberDecimals, maxNumberDecimals);
    }

    public BigInteger nextBigInteger() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextBigInteger()) {
            throw new InputMismatchException();
        }
        String bigIntegerString = sc.next();
        if (!isNormalIntegerString(bigIntegerString)) {
            throw new NumberFormatException(String.format("%s: Not normal integer", bigIntegerString));
        }
        return new BigInteger(bigIntegerString);
    }

    @Deprecated
    public BigInteger readBigInteger() {
        return nextBigInteger();
    }

    public BigDecimal nextBigDecimal() {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextBigDecimal()) {
            throw new InputMismatchException();
        }
        String bigDecimalString = sc.next();
        if (!isNormalFloatingPoint(bigDecimalString)) {
            throw new InputMismatchException();
        }
        return new BigDecimal(bigDecimalString);
    }

    @Deprecated
    public BigDecimal readBigDecimal() {
        return nextBigDecimal();
    }

    public BigDecimal nextBigDecimal(int minNumberDecimals, int maxNumberDecimals) {
        assertIsNotEof();
        assertNextCharacterIsNotWhiteSpace();
        sc.useDelimiter(JWS);
        if (!sc.hasNextBigDecimal()) {
            throw new InputMismatchException();
        }
        String bigDecimalString = sc.next();
        if (!isNormalFloatingPoint(bigDecimalString)) {
            throw new InputMismatchException();
        }
        int numberDecimals = numberDecimals(bigDecimalString);
        if (numberDecimals < minNumberDecimals || maxNumberDecimals < numberDecimals) {
            throw new InputMismatchException();
        }
        return new BigDecimal(bigDecimalString);
    }

    @Deprecated
    public BigDecimal readBigDecimal(int minNumberDecimals, int maxNumberDecimals) {
        return nextBigDecimal(minNumberDecimals, maxNumberDecimals);
    }

    public String nextLineBreak() {
        assertIsNotEof();
        sc.useDelimiter("");
        if (sc.hasNext(EOL)) {
            String lf = sc.next();
            return lf;
        } else {
            throw new InputMismatchException();
        }
    }

    @Deprecated
    public String readLineBreak() {
        return nextLineBreak();
    }

    public boolean isEof() {
        sc.useDelimiter("");
        return !sc.hasNext();
    }

    public void assertIsEof() {
        if (!isEof()) {
            throw new AssertionError();
        }
    }

    public void close() {
        sc.close();
    }

    private void assertIsNotEof() {
        if (isEof()) {
            throw new NoSuchElementException();
        }
    }

    private void assertNextCharacterIsNotWhiteSpace() {
        sc.useDelimiter("");
        if (sc.hasNext(JWS)) {
            throw new InputMismatchException();
        }
    }

    private static boolean isNormalIntegerString(String str) {
        return NORMAL_INTEGER.matcher(str).matches();
    }

    private static boolean isNormalFloatingPoint(String str) {
        return NORMAL_FLOATING_POINT.matcher(str).matches();
    }

    private static int numberDecimals(String str) {
        if (str.indexOf('.') < 0) {
            return 0;
        } else {
            return str.length() - str.indexOf('.') - 1;
        }
    }
}


class AssertionUtils {
    public static final String DIGITS = "0123456789";
    public static final String LARGE_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String SMALL_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABETS = LARGE_ALPHABETS + SMALL_ALPHABETS;
    public static final String DIGIT_OR_ALPHABETS = DIGITS + ALPHABETS;

    private AssertionUtils() {

    }

    public static void assertTrue(boolean val) {
        if (!val) {
            throw new AssertionError();
        }
    }

    public static void assertFalse(boolean val) {
        if (val) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(int val, int min, int max) {
        if (val < min) {
            throw new AssertionError();
        }
        if (val > max) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(long val, long min, long max) {
        if (val < min) {
            throw new AssertionError();
        }
        if (val > max) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(double val, double min, double max) {
        if (Double.isNaN(val)) {
            throw new AssertionError();
        }
        if (val < min) {
            throw new AssertionError();
        }
        if (val > max) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(BigInteger val, BigInteger min, BigInteger max) {
        if (val.compareTo(min) < 0) {
            throw new AssertionError();
        }
        if (val.compareTo(max) > 0) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(BigDecimal val, BigDecimal min, BigDecimal max) {
        if (val.compareTo(min) < 0) {
            throw new AssertionError();
        }
        if (val.compareTo(max) > 0) {
            throw new AssertionError();
        }
    }

    public static void assertInRange(int[] vals, int min, int max) {
        for (int val : vals) {
            assertInRange(val, min, max);
        }
    }

    public static void assertInRange(long[] vals, long min, long max) {
        for (long val : vals) {
            assertInRange(val, min, max);
        }
    }

    public static void assertInRange(double[] vals, double min, double max) {
        for (double val : vals) {
            assertInRange(val, min, max);
        }
    }

    public static void assertInRange(BigInteger[] vals, BigInteger min, BigInteger max) {
        for (BigInteger val : vals) {
            assertInRange(val, min, max);
        }
    }

    public static void assertInRange(BigDecimal[] vals, BigDecimal min, BigDecimal max) {
        for (BigDecimal val : vals) {
            assertInRange(val, min, max);
        }
    }

    public static void assertInArray(String str, String[] candidates) {
        if (Arrays.asList(candidates).indexOf(str) < 0) {
            throw new AssertionError();
        }
    }

    public static void assertInCharList(char ch, String charList) {
        if (charList.indexOf(ch) < 0) {
            throw new AssertionError();
        }
    }

    public static void assertInCharList(String str, String charList) {
        for (char c : str.toCharArray()) {
            assertInCharList(c, charList);
        }
    }

    public static void assertAllDifferent(char[] vals) {
        List<Character> list = new ArrayList<Character>();
        for (char val : vals) {
            list.add(val);
        }
        assertAllDifferent(list);
    }

    public static void assertAllDifferent(int[] vals) {
        List<Integer> list = new ArrayList<Integer>();
        for (int val : vals) {
            list.add(val);
        }
        assertAllDifferent(list);
    }

    public static void assertAllDifferent(long[] vals) {
        List<Long> list = new ArrayList<Long>();
        for (long val : vals) {
            list.add(val);
        }
        assertAllDifferent(list);
    }

    public static void assertAllDifferent(double[] vals) {
        List<Double> list = new ArrayList<Double>();
        for (double val : vals) {
            list.add(val);
        }
        assertAllDifferent(list);
    }

    public static void assertAllDifferent(BigInteger[] vals) {
        assertAllDifferent(Arrays.asList(vals));
    }

    public static void assertAllDifferent(BigDecimal[] vals) {
        assertAllDifferent(Arrays.asList(vals));
    }

    public static void assertAllDifferent(String[] vals) {
        assertAllDifferent(Arrays.asList(vals));
    }

    public static <T> void assertAllDifferent(T[] vals) {
        assertAllDifferent(Arrays.asList(vals));
    }

    private static <T> void assertAllDifferent(Collection<T> vals) {
        Set<T> set = new HashSet<T>();
        for (T val : vals) {
            if (set.contains(val)) {
                throw new AssertionError();
            } else {
                set.add(val);
            }
        }
    }
}