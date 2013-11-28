package src;

public class SurveillanceSystem {
    public String getContainerInfo(String containers, int[] reports, int L) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < containers.length(); i++) {
            res.append(solve(containers, reports, L, i));
        }
        return res.toString();
    }
    private char solve(String containers, int[] reports, int L, int p) {
        int N = containers.length();
        int[] reportCount = new int[L + 1];
        for (int report : reports) reportCount[report]++;
        int[] num = new int[N - L + 1];
        boolean[] see = new boolean[N - L + 1];
        boolean maySeen = false;
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < L; j++) {
                if (containers.charAt(i + j) == 'X') {
                    num[i]++;
                }
                if (i + j == p) see[i] = true;
            }
            if (see[i] && reportCount[num[i]] > 0) maySeen = true;
        }
        int[] countWithout = new int[L+1];
        for(int i=0;i<num.length;i++)if(!see[i])countWithout[num[i]]++;
        boolean mayIgnored = true;
        for(int i=0;i<countWithout.length;i++)if(countWithout[i] < reportCount[i])mayIgnored = false;
        return mayIgnored ? maySeen ? '?' : '-' : '+';
    }
}
