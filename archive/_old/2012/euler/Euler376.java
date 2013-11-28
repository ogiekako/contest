package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.counter.Counter;
import net.ogiekako.algorithm.utils.counter.TreeCounter;

import java.io.PrintWriter;
import java.util.Arrays;

public class Euler376 {
    private static final int FACE = 6;
    private static final int DICE = 3;
    private static final int WIN_SATURATION = FACE * FACE / 2 + 1;
    int N;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        N = in.nextInt();
        State init = new State(new int[DICE], new int[DICE]);
        Counter<State> curCounter = new TreeCounter<State>();
        curCounter.add(init, 1L);
        for (int i = 0; i < N; i++) {
            Counter<State> nextCounter = new TreeCounter<State>();
            for (State state : curCounter.keySet()) {
                long comb = curCounter.get(state);
                state.addNextStates(comb, nextCounter);
            }
            curCounter = nextCounter;
        }
        long res = 0;
        for (State state : curCounter.keySet()) {
            if (state.isValid()) {
                res += curCounter.get(state);
            }
        }
        out.println(res / DICE);
    }

    class State implements Comparable<State> {
        int[] positions;
        int[] wins;

        State(int[] positions, int[] wins) {
            this.positions = positions;
            this.wins = wins;
        }

        public void addNextStates(long combination, Counter<State> nextCounter) {
            dfs(0, positions[0], combination, nextCounter);
        }

        private void dfs(int person, int previousPositionOfFirstPerson, long combination, Counter<State> nextCounter) {
            if (person == DICE) {
                State nxt = new State(positions.clone(), wins.clone());
                nextCounter.add(nxt, combination);
                return;
            }
            int nextPerson = person + 1;
            if (nextPerson >= DICE) nextPerson -= DICE;
            int tmpPos = positions[person];
            int tmpWin = wins[person];
            for (int i = positions[person]; i <= FACE; i++) {
                dfs(person + 1, previousPositionOfFirstPerson, combination, nextCounter);
                positions[person] = i + 1;
                wins[person] += nextPerson == 0 ? previousPositionOfFirstPerson : positions[nextPerson];
                if (wins[person] >= WIN_SATURATION) wins[person] = WIN_SATURATION;
            }
            positions[person] = tmpPos;
            wins[person] = tmpWin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (!Arrays.equals(positions, state.positions)) return false;
            if (!Arrays.equals(wins, state.wins)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(positions);
            result = 31 * result + Arrays.hashCode(wins);
            return result;
        }

        public boolean isValid() {
            return ArrayUtils.allOf(positions, FACE) && ArrayUtils.allOf(wins, WIN_SATURATION);
        }

        public int compareTo(State o) {
            int tmp = ArrayUtils.compare(positions, o.positions);
            if (tmp != 0) return tmp;
            return ArrayUtils.compare(wins, o.wins);
        }
    }
}
