package tmp;

// Paste me into the FileEdit configuration dialog

public class EvenRoute {
    public String isItPossible(int[] x, int[] y, int wantedParity) {
        for (int i = 0; i < x.length; i++) {
            if ((x[i] + y[i] & 1) == wantedParity) return "CAN";
        }
        return "CANNOT";
    }
}
