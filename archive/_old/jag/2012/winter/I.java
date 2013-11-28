package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.Parser;

import java.io.PrintWriter;

public class I {
    /*
http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2348
再帰の展開.
スタックを使って,自前で管理しないと,stack overflow となる.
多分,まず再帰で書いて書きなおすほうがいい.
<expression> ::= <term> | <expression> "|" <term>
<term> ::= <factor> | <term> "&" <factor>
<factor> ::= <variable> | "~" <factor> | "(" <expression> ")"
<variable> ::= "x" <number>
<number> ::= "1" | "2" |... | "999999" | "1000000"
     */
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        char[] cs = (in.next() + "=").toCharArray();
        int res = Parser.TestingCircuits.solve(cs);
        out.println(res);
    }
}
