package on2012_4_30.electionfrauddiv1;



// Paste me into the FileEdit configuration dialog

public class ElectionFraudDiv1 {
    public int MinimumVoters(int[] percentages) {
        for (int N = 1; N < 210; N++) {
            if (can(N, percentages)) return N;
        }
        return -1;
    }

    private boolean can(int N, int[] percentages) {
        int min = 0, max = 0;
        for(int P:percentages){
            min += Math.max(0, (N*(2*P-1)+199)/200);
            max += (N*(2*P+1)-1)/200;
        }
        return min <= N && N <= max;
    }
}

