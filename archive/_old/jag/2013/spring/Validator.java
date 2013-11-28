package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

import static src.AssertionUtils.assertInRange;

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

    Point o1, x1, y1;
    Point o2, x2, y2;
    double RANGE = 3.0;
    double EPS1 = 1e-6;
    double EPS2 = 1e-3;

    public void validate() {
        read();
        check();
    }

    private void check() {
        log("checkRange");
        checkRange();
        log("checkOrthonormal");
        checkOrthonormal();
        log("checkNotTouched");
        checkNotTouched();
    }

    private void checkNotTouched() {

    }

    private void checkOrthonormal() {
        for (Point p : new Point[]{x1, y1, x2, y2}) {
            assertInRange(p.norm(), 1.0 - EPS1, 1.0 + EPS1);
        }
        assertInRange(x1.dot(y1), -EPS1, EPS1);
        assertInRange(x2.dot(y2), -EPS1, EPS1);
    }

    private void checkRange() {
        for (Point p : new Point[]{o1, x1, y1, o2, x2, y2}) {
            for (double x : new double[]{p.x, p.y, p.z}) {
                assertInRange(x, -RANGE, RANGE);
            }
        }
    }

    private void read() {
        o1 = readPoint(); in.nextLineBreak();
        x1 = readPoint(); in.nextWhiteSpace(); y1 = readPoint(); in.nextLineBreak();
        o2 = readPoint(); in.nextLineBreak();
        x2 = readPoint(); in.nextWhiteSpace(); y2 = readPoint(); in.nextLineBreak();
        in.assertIsEof();
    }

    private Point readPoint() {
        Point res = new Point();
        res.x = in.nextDouble();
        in.nextWhiteSpace();
        res.y = in.nextDouble();
        in.nextWhiteSpace();
        res.z = in.nextDouble();
        return res;
    }

    private void log(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    class Point {
        double x, y, z;

        public double norm() {
            return Math.sqrt(dot(this));
        }

        public double dot(Point o) {
            return x * o.x + y * o.y + z * o.z;
        }
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