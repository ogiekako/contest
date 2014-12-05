package src;

public class Target {
    public String[] draw(int n) {
        char[][] res = new char[n][n];
        for (int i=0;i<n;i++){
            int len = n-i*2;
            for(int j=0;j<len;j++)for(int k=0;k<len;k++)res[i+j][i+k]=i%2==0?'#':' ';
        }
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) {
            ss[i] = new String(res[i]);
        }
        return ss;
    }
}
