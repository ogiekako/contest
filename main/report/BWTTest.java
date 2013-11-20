package report;
import org.junit.Test;

import java.util.Random;
public class BWTTest {
    @Test
    public void testSA(){
        int n = 50;
        Random rnd = new Random(124829048L);
        for(int i=0;i<100;i++){
            String s = "";
            for(int j=0;j<n;j++){
                s = s + (char)(rnd.nextInt(10) + 'a') ;
            }
            int[] sa =
        }
    }
}
