package report;
import java.util.Scanner;
public class RLE {
    public static void main(String[] args) {
        new RLE().run();
    }
    private void run() {
        char[] cs = read();
        int n = cs.length;
        boolean abs = false;
        for(int i=0;i<n;){
            if(!abs){
                int cnt = 0;
                for(int j=0;j<3 && i+j+1 < n; j++){
                    if(cs[i+j] != cs[i+j+1])cnt++;
                }
                if(cnt >= 2) abs = true;
                if(!abs){
                    int j = i;
                    while(j<n && cs[i] == cs[j])j++;
                    System.out.printf("%d%c",j-i,cs[i]);
                    i=j;
                }
            } else{
                int cnt = 1;
                int j;
                for(j=i+1;j<n && cnt<3;j++){
                    if(cs[j] == cs[j-1])cnt++;
                    else cnt = 1;
                }
                System.out.printf("0");
                if(cnt == 3){
                    abs = false;
                    j -= 3;
                }
                System.out.printf("%d",j-i);
                for(int k=i;k<j;k++) System.out.printf("%c",cs[k]);
                i = j;
            }
        }
    }
    private char[] read() {
        StringBuilder b = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s = sc.nextLine();
            b.append(s);
            b.append("\n");
        }
        return b.toString().toCharArray();
    }
}
