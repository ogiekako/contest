package net.ogiekako.algorithm.math.sigma;
import net.ogiekako.algorithm.utils.Pair;

import java.util.List;
import java.util.Scanner;
public class SigmaComputer {
    public static void main(String[] args) {
        new SigmaComputer().solve();
    }
    private void solve() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine().replaceAll(" ", "");

    }

    interface Var {
    }

    class EPS implements Var {}

    class C implements Var {}

    class ID implements Var {
        String id;
    }

    class Cond {
        int mod;
        List<Pair<Var, Integer>> vs;

        public Cond(Integer mod, List<Pair<Var, Integer>> vs) {
            this.mod = mod;
            this.vs = vs;
        }
    }

    interface Conds extends List<Cond> {

    }

    interface Term extends List<Pair<String, Integer>> {

    }

    class Exp {
        List<Pair<Term, Integer>> ts;
        int d;
    }

    interface Program extends List<Pair<Conds, Exp>> {

    }

    interface Raw {
        Program g();
    }

    class Add implements Raw {
        Raw e1, e2;
        public Program g() {
            Program p1 = e1.g(), p2 = e2.g();
            p1.addAll(p2);
            return p1;
        }
    }

    class Mul implements Raw {
        Raw e1, e2;
        public Program g() {
            Program p1 = e1.g(), p2 = e2.g();
//            Program prog = new Program();

            return null;
        }
    }

    class Sum implements Raw {
        String id;
        Raw raw;
        public Program g() {
            Program p = raw.g();
            System.err.printf("Sum@%s\n%d -> ", id, p.size());
//            p = trans(p);
            System.err.printf("%d\n", p.size());
            return p;
        }
//        public Program trans(Program p) {
//
//
//        }
//
//        private Program sum_exp(Pair<Conds, Exp> line) {
//
//        }

    }

}
// 問題集:

// Backus-Naur form
// http://ja.wikipedia.org/wiki/%E3%83%90%E3%83%83%E3%82%AB%E3%82%B9%E3%83%BB%E3%83%8A%E3%82%A6%E3%82%A2%E8%A8%98%E6%B3%95
//
// example: $a$b$c$d$e[a<b][b<c][c<d][d<e][a<=S1][a+b<=S2][a+b+c<=S3][a+b+c+d<=S4][a+b+c+d+e<=S5]
// <expr> ::= $ <var> <expr> | <linear> <expr> |
// <cond> ::= "[" <linear> <eq> <linear> "]"
// <linear> ::=
// <var> ::= <alphabet> | <alphabet> <number>
// <alphabet> ::= [A-Z][a-z]
// <number> ::= [0-9]+
