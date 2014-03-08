package on2013_11.on2013_11_20_TopCoder_SRM__496.AnagramFree;



import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
public class AnagramFree {
    public int getMaximumSubset(String[] S) {
        Set<String> set = new TreeSet<>();
        for(String s : S){
            char[] cs = s.toCharArray() ;
            Arrays.sort(cs);
            set.add(String.valueOf(cs));
        }
        return set.size();
    }
}
