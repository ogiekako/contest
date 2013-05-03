package net.ogiekako.algorithm.dataStructure;

public class LowerBound{

    /**
     * whereはソート済みであること.
     * where[i] < what なる最大の i を返す.
     * 存在しなければ,-1 を返す.
     * @param where
     * @param what
     * @return
     */
    int upperBound(long[] where,long what) {
    	int left=-1,right = where.length;
    	do {
    		int mid=(left+right)/2;
    		if(where[mid] < what)left=mid;
    		else right=mid;
    	}while(right-left>1);
    	return left;
    }
    /**
     * whereはソート済みであること.
     * where[i] >= what なる最小の i を返す.
     * 存在しなければ, where.length を返す.
     * upperBound(where,what) + 1 と同じ.
     * @param where
     * @param what
     * @return
     */
    int lowerBound(long[] where,long what) {
    	int left=-1,right = where.length;
    	do {
    		int mid=(left+right)/2;
    		if(where[mid]<what)left=mid;
    		else right=mid;
    	}while(right-left>1);
    	return right;
    }
}
