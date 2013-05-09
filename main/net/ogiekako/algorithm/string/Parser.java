package net.ogiekako.algorithm.string;

import net.ogiekako.algorithm.dataStructure.intCollection.IntStack;

public class Parser {
    /**
     * Testing Circuits
     * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=2348
     * 再帰の展開.
     * スタックを使って,自前で管理しないと,stack overflow となる.
     * 多分,まず再帰で書いて書きなおすほうがいい.
     * <expression> ::= <term> | <expression> "|" <term>
     * <term> ::= <factor> | <term> "&" <factor>
     * <factor> ::= <variable> | "~" <factor> | "(" <expression> ")"
     * <variable> ::= "x" <number>
     * <number> ::= "1" | "2" |... | "999999" | "1000000"
     */
    public static class TestingCircuits {

        static final int M = 1000000007;

        public static int solve(char[] cs) {
//            this.cs=cs;this.p=0;int res = (int) expression()[0];return res;
            int p = 0;
            IntStack dataStack = new IntStack(0);
            IntStack programStack = new IntStack(0);
            programStack.push(0);
            while (!programStack.isEmpty()) {
                int phase = programStack.pop();
                switch (phase) {
                    case 0:
                        dataStack.push(1);// cntFalse
                        dataStack.push(1);// cntAll
                        programStack.push(1);
                        programStack.push(2);
                        break;
                    case 1:
                        int t0 = dataStack.pop();
                        int t1 = dataStack.pop();
                        int cntAll = dataStack.pop();
                        int cntFalse = dataStack.pop();
                        cntFalse = (int) ((long) cntFalse * t1 % M);
                        cntAll = (int) ((long) cntAll * (t0 + t1) % M);
                        if (cs[p] != '|') {
                            dataStack.push(cntFalse);
                            dataStack.push((cntAll - cntFalse + M) % M);
                        } else {
                            p++;
                            dataStack.push(cntFalse);
                            dataStack.push(cntAll);
                            programStack.push(1);
                            programStack.push(2);
                        }
                        break;
                    case 2:
                        dataStack.push(1);// cntAll
                        dataStack.push(1);// cntTrue
                        programStack.push(3);
                        programStack.push(4);
                        break;
                    case 3:
                        t0 = dataStack.pop();
                        t1 = dataStack.pop();
                        int cntTrue = dataStack.pop();
                        cntAll = dataStack.pop();
                        cntTrue = (int) ((long) cntTrue * t0 % M);
                        cntAll = (int) ((long) cntAll * (t0 + t1) % M);
                        if (cs[p] != '&') {
                            dataStack.push((cntAll - cntTrue + M) % M);
                            dataStack.push(cntTrue);
                        } else {
                            p++;
                            dataStack.push(cntAll);
                            dataStack.push(cntTrue);
                            programStack.push(3);
                            programStack.push(4);
                        }
                        break;
                    case 4:
                        if (cs[p] == '(') {
                            p++;
                            programStack.push(5);
                            programStack.push(0);
                        } else if (cs[p] == '~') {
                            p++;
                            programStack.push(6);
                            programStack.push(4);
                        } else {
                            if (cs[p++] != 'x') throw new RuntimeException();
                            while (Character.isDigit(cs[p])) p++;
                            dataStack.push(1);
                            dataStack.push(1);
                        }
                        break;
                    case 5:
                        p++;
                        break;
                    case 6:
                        t0 = dataStack.pop();
                        t1 = dataStack.pop();
                        dataStack.push(t0);
                        dataStack.push(t1);
                        break;
                }
            }
            if (cs[p] != '=') throw new RuntimeException();
            return dataStack.pop();
        }

        int p;
        char[] cs;

        private long[] expression() {
            long cntFalse = 1;//0
            long cntAll = 1;
            for (; ; ) {
                long[] t = term();
                // 1
                cntFalse = cntFalse * t[1] % M;
                cntAll = cntAll * (t[0] + t[1]) % M;
                if (cs[p] != '|') break;
                p++;
            }
            return new long[]{(cntAll - cntFalse + M) % M, cntFalse};
        }

        private long[] term() {
            long cntTrue = 1;// 2
            long cntAll = 1;
            for (; ; ) {
                long[] t = factor();
                // 3
                cntTrue = cntTrue * t[0] % M;
                cntAll = cntAll * (t[0] + t[1]) % M;
                if (cs[p] != '&') break;
                p++;
            }
            return new long[]{cntTrue, (cntAll - cntTrue + M) % M};
        }

        private long[] factor() {// 4
            if (cs[p] == '(') {
                p++;
                long[] ts = expression();
                p++;// 5
                return ts;
            }
            if (cs[p] == '~') {
                p++;
                long[] ts = factor();
                // 6
                return new long[]{ts[1], ts[0]};
            }
            if (cs[p++] != 'x') throw new RuntimeException();
            while (Character.isDigit(cs[p])) p++;
            return new long[]{1, 1};
        }
    }
}
