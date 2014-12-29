package src;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TheKingsFactorization {
    public long[] getVector(long N, long[] primes) {
        for (long p : primes) N /= p;
        List<Long> res = new ArrayList<>();
        for (long i=2;i<1e6;i++)while(N%i==0){
            res.add(i);
            N/=i;
        }
        if(N>1)res.add(N);
        long[] res2 = new long[res.size() + primes.length];
        for (int i = 0; i < primes.length; i++) {
            res2[2*i] = primes[i];
            if (i < res.size())
                res2[2*i+1] = res.get(i);
        }
        return res2;
    }

    public static void main(String[] args) {
        long p;
        Random rnd = new Random();
        for(int q= (int) 1e9;;q--){
            if(2L*q*q < 1e18) {
                if(MathUtils.isPrime(q)){
                    System.out.println(q);
                    System.out.println(2L*q*q);
                    break;
                }
            }
//            p = rnd.nextLong() / 100;
//            if (BigInteger.valueOf(p).isProbablePrime(100)){
//                if (0 < p*2 && p < 1e18) {
//                    System.out.println(p*2);
//                    break;
//                }
//            }
//            if (MathUtils.isPrime(p))break;
        }
//        System.out.println(q);
//        System.out.println(p);
//        long PP = (long) p * p;
//        System.out.println(PP);
    }
}
