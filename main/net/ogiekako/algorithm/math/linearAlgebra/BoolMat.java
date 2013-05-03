package net.ogiekako.algorithm.math.linearAlgebra;

public class BoolMat{
	// rankA O(n m max{n,m})    vf.srm306B
	public static int rank(boolean[][] A){
		int n=A.length,m=A[0].length;
		int r=0;
		for(int i=0;r<n&&i<m;++i){
			int pivot=-1;
			for(int j=r;j<n;j++)
				if(A[j][i])
					pivot=j;
			if(pivot==-1)continue;
			boolean[] tmp=A[pivot];A[pivot]=A[r];A[r]=tmp;
			for(int j=r+1;j<n;j++)if(A[j][i])
				for(int k=i;k<m;k++)
					A[j][k]^=A[r][k];
			++r;
		}
		return r;
	}
    
}
