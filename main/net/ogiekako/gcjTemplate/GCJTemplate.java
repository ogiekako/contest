package net.ogiekako.gcjTemplate;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.*;

import static java.util.Arrays.deepToString;

public abstract class GCJTemplate {
    public static enum MODE {
        SAMPLE, SMALL, LARGE, SMALL_PRACTICE, LARGE_PRACTICE
    }

    protected abstract MODE mode();
    protected abstract void prepare();
    protected abstract void solve(MyScanner in, MyPrintWriter out);

    final String _FOLDER = "";
    final String _CLASS_NAME = this.getClass().getSimpleName();
    final String _PROBLEM_ID = _CLASS_NAME.substring(_CLASS_NAME.length() - 1);
    final String _PROBLEM = _FOLDER + _PROBLEM_ID;

    public void debug(Object... os) {
        System.err.println(deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        if (testNumber == 1) prepare();
        if (mode() == MODE.SAMPLE) {
            out.printFormat("Case #%d: ", testNumber);
            solve(in, out);
        } else{
            try {
                MyScanner fileIn = new MyScanner(new FileInputStream(inFile()));
                MyPrintWriter fileOut = new MyPrintWriter(new FileOutputStream(outFile()));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String outFile(){
        return fileName() + ".out";
    }

    private String inFile() {
        return fileName() + ".in";
    }

    private String fileName(){
        if(isSmall())return _smallName();
        else return _largeName();
    }

    private String _smallName() {
        int i = 0;
        while(new File(_smallName(i) + ".in").exists())i++;
        i--;
        return _smallName(i);
    }

    private void main() throws IOException {
        if (mode() != MODE.SAMPLE) {
            if (isSmall()) {
                int i = 0;
                while (new File(_smallName(i) + ".in").exists())
                    i++;
                i--;
                boolean test = false;
                if (new File(_smallName(i) + ".out").exists()) {
                    System.err.println("overwrite?(y/n)");
                    char c = (char) System.in.read();
                    test = c != 'y';
                }
                if (test) {
                    System.setIn(new FileInputStream(_smallName(i) + ".in"));
                    System.setOut(new PrintStream(_PROBLEM + "-small-test.out"));
                    FileReader f1 = new FileReader(_PROBLEM + "-small-test.out");
                    FileReader f2 = new FileReader(_smallName(i) + ".out");
                    BufferedReader br1 = new BufferedReader(f1);
                    BufferedReader br2 = new BufferedReader(f2);
                    for (int j = 1; ; j++) {
                        String s1 = br1.readLine();
                        String s2 = br2.readLine();
                        if (s1 == null && s2 == null) {
                            System.err.println("OK!");
                            break;
                        }
                        if (s1 == null || s2 == null || !s1.equals(s2)) {
                            System.err.println("failed at line " + j);
                            System.err.println("expected " + s2);
                            System.err.println("but " + s1);
                            break;
                        }
                    }
                } else {
                    System.setIn(new FileInputStream(_smallName(i) + ".in"));
                    System.setOut(new PrintStream(_smallName(i) + ".out"));
//                    _doIt();
                }
            } else {
                System.setIn(new FileInputStream(_largeName() + ".in"));
                System.setOut(new PrintStream(_largeName() + ".out"));
//                _doIt();
            }
//        } else _doIt();
        }
    }

    private boolean isSmall() {
        return mode() == MODE.SMALL || mode() == MODE.SMALL_PRACTICE;
    }

    private String _largeName() {
        return _PROBLEM + "-large" + (isPractice() ? "-practice" : "");
    }

    private boolean isPractice() {
        return mode() == MODE.SMALL_PRACTICE || mode() == MODE.LARGE_PRACTICE;
    }

    private String _smallName(int a) {
        return _PROBLEM + "-small" + (isPractice() ? a == 0 ? "-practice" : "" : "-attempt" + a);
    }
}
