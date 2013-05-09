package net.ogiekako.algorithm.math;

public class MathUtilsMinor {
    /**
     * f(a,n) = n==0 ? 1 : a^f(a,n-1).
     * べき乗の鎖 chain
     * Euler118, http://projecteuler.net/index.php?section=problems&id=188
     * http://livearchive.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=&problem=2146&mosmsg=Submission+received+with+ID+967236
     */
    public static long tetration(long a, long n, int modulo) {
        if (a < 0 || n < 0 || modulo <= 0) throw new IllegalArgumentException();
        if (n == 0) return 1 % modulo;
        if (a == 0) return (1 - n % 2) % modulo;
        if (a <= 1) return a % modulo;
        return tetration0(a, n, modulo).residue;
    }

    private static EntryTetration tetration0(long a, long n, int modulo) {
        if (n == 1) return new EntryTetration(a, a % modulo);
        int th = modulo * 2;
        if (modulo == 1) {
            return new EntryTetration(th, 0);
        }
        int totient = TotientFuction.tot(modulo);
        EntryTetration power = tetration0(a, n - 1, totient);
        if (power.real < totient * 2) {
            long residue = MathUtils.powMod(a, power.real, modulo);
            long value = 1;
            for (int i = 0; i < power.real; i++) {
                value *= a;
                if (value >= th) break;
            }
            return new EntryTetration(value, residue);
        } else {
            // periodic after totient.
            long residue = MathUtils.powMod(a, totient + power.residue, modulo);
            return new EntryTetration(th, residue);
        }
    }

    /**
     * f(n,a,b) = n==0 ? b+1
     * ; n==1 && b==0 ? a
     * ; n==2 && b==0 ? b
     * : n>=3 && b==0 ? 1
     * : f(n-1,a,f(n,a,b-1).
     * <p/>
     * http://livearchive.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=&problem=2146&mosmsg=Submission+received+with+ID+967236
     */
    public static long hyperexponentiation(int n, long a, long b, int mod) {
        if (n < 0 || a < 0 || b < 0 || mod <= 0) throw new IllegalArgumentException();
        if (n == 0) return (b + 1) % mod;
        if (n == 1) return (a + b) % mod;
        if (n == 2) return a * b % mod;
        if (n == 3) return MathUtils.powMod(a, b, mod);
        if (n == 4) return tetration(a, b, mod);
        if (b == 0) return 1 % mod;
        if (a == 0) {
            return (1 - b % 2) % mod;
        }
        if (a == 1 || b == 1) return a % mod;
        if (a == 2 && b == 2) return 4 % mod;
        int tower = towerCount(n, a, b, mod);
        return tetration(a, tower, mod);
    }

    private static int towerCount(int n, long a, long b, int th) {
        if (n == 4) return (int) Math.min(b, th);
        if (n == 5 && b == 2) return (int) Math.min(a, th);
        if (n == 5 && b == 3 && a == 2) return Math.min(4, th);
        if (n == 5 && b == 4 && a == 2) return Math.min(65536, th);
        // n==5,b==3,a==3 -> 3^27
        if (n == 6 && b == 3 && a == 2) return Math.min(65536, th);
        return th;
    }

    private static class EntryTetration {
        long real;
        long residue;
        EntryTetration(long real, long residue) {
            this.real = real;
            this.residue = residue;
        }
    }
}
