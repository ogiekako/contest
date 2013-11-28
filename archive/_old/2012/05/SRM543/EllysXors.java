package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

public class EllysXors {
    public long getXor(long L, long R) {
        return MathUtils.xorSum(R + 1) ^ MathUtils.xorSum(L);
    }
}

