package net.ogiekako.algorithm.string;

import net.ogiekako.algorithm.dataStructure.persistent.PersistentLinkedList;
import net.ogiekako.algorithm.math.ModuloEquation;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;
import net.ogiekako.algorithm.utils.Pair;
import net.ogiekako.algorithm.utils.interfaces.Function;

import java.util.*;

/**
 * http://www.prefield.com/algorithm/string/aho_corasick.html
 *
 * problems:
 * CodeChef JULY12 FAVNUM
 * 
 */
public class AhoCorasick {
    private final int[] ctoi;
    private final int letterCount;

    private AutomatonState root;
    private String[] patterns;
    private List<AutomatonState> states = new ArrayList<AutomatonState>();

    public AhoCorasick(String usedLetters) {
        letterCount = usedLetters.length();
        ctoi = new int[256];
        Arrays.fill(ctoi, -1);
        for (int i = 0; i < letterCount; i++) ctoi[usedLetters.charAt(i)] = i;
    }

    public void match(String str, Function<Pair<Integer, PersistentLinkedList<String>>, Void> funcRecvMatchListFromLonger) {
        AutomatonState v = root;
        for (int strIndex = 0; strIndex < str.length(); strIndex++) {
            int i = ctoi[str.charAt(strIndex)];

            while (v.nextStates[i] == null) {
                v = v.failureLink;
            }
            v = v.nextStates[i];
            if (v.matchListFromLonger != null)
                funcRecvMatchListFromLonger.f(new Pair<Integer, PersistentLinkedList<String>>(strIndex, v.matchListFromLonger));
        }
    }

    public void construct(String[] patterns) {
        root = new AutomatonState(letterCount, states.size());
        states.add(root);
        this.patterns = patterns;
        generateTrieTree();
        generateFailureFunction();
    }

    private void generateFailureFunction() {
        Queue<AutomatonState> que = new LinkedList<AutomatonState>();
        for (int i = 0; i < letterCount; i++) {
            if (root.nextStates[i] != null) {
                root.nextStates[i].failureLink = root;
                que.offer(root.nextStates[i]);
            } else {
                root.nextStates[i] = root;
            }
        }
        while (!que.isEmpty()) {
            // failure link has been made for v.
            AutomatonState v = que.poll();
            for (int i = 0; i < letterCount; i++)
                if (v.nextStates[i] != null) {
                    que.offer(v.nextStates[i]);
                    AutomatonState t = v.failureLink;
                    while (t.nextStates[i] == null) t = t.failureLink;
                    v.nextStates[i].failureLink = t.nextStates[i];
                    appendMatchList(v.nextStates[i]);
                }
        }
    }

    private void appendMatchList(AutomatonState v) {
        AutomatonState f = v.failureLink;
        if (f.matchListFromLonger != null) {
            if (v.matchListFromLonger != null)
                v.matchListFromLonger = PersistentLinkedList.cons(v.matchListFromLonger.car(), f.matchListFromLonger);
            else if (v.matchListFromLonger == null) v.matchListFromLonger = f.matchListFromLonger;
        }
    }

    private void generateTrieTree() {
        for (String pattern : patterns) {
            AutomatonState v = root;
            for (char c : pattern.toCharArray()) {
                int i = ctoi[c];
                if (v.nextStates[i] == null) {
                    v.nextStates[i] = new AutomatonState(letterCount, states.size());
                    states.add(v.nextStates[i]);
                }
                v = v.nextStates[i];
            }
            v.matchListFromLonger = new PersistentLinkedList<String>(pattern);
        }
    }

    // poj 2778
    public int countUnmatchStringOf(long length, int modulus) {
        List<AutomatonState> allAutomatonState = allStates();
        int[] indices = new int[allAutomatonState.size()];
        Arrays.fill(indices, -1);
        int stateCount = 0;
        for (int i = 0; i < allAutomatonState.size(); i++) {
            AutomatonState automatonState = allAutomatonState.get(i);
            if (automatonState.matchListFromLonger == null) indices[i] = stateCount++;
        }
        long[][] A = new long[stateCount][stateCount];
        for (int i = 0; i < indices.length; i++) {
            int id = indices[i];
            if (id == -1) continue;
            AutomatonState cur = allAutomatonState.get(i);
            for (int j = 0; j < letterCount; j++) {
                AutomatonState v = cur;
                while (v.nextStates[j] == null) v = v.failureLink;
                int nId = indices[allAutomatonState.indexOf(v.nextStates[j])];
                if (nId >= 0) {
                    A[nId][id] = ModuloEquation.plus((int) A[nId][id], 1, modulus);
                }
            }
        }
        A = Matrix.powered(A, length, modulus);
        int res = 0;
        for (int i = 0; i < stateCount; i++) res = ModuloEquation.plus(res, (int) A[i][0], modulus);
        return res;
    }

    public List<AutomatonState> allStates() {
        return states;
    }

}
