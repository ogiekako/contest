package net.ogiekako.algorithm.io;

import java.io.*;
import java.util.Arrays;

public class MyPrintWriter {
    PrintWriter out;

    public MyPrintWriter(OutputStream outputStream) {
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public MyPrintWriter(Writer writer) {
        out = new PrintWriter(writer);
    }

    public void debug(Object... os) {
        println("dbg : " + Arrays.deepToString(os));
    }

    public void println(Object... os) {
        if (os.length == 0) {
            out.println();
            return;
        }
        for (int i = 0; i < os.length - 1; i++) {
            out.print(os[i]);
            out.print(' ');
        }
        out.println(os[os.length - 1]);
    }

    public void close() {
        out.close();
    }

    public void flush() {
        out.flush();
    }

    public void printFormat(String format, Object... args) {
        out.printf(format, args);
    }

}
