package src;

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
