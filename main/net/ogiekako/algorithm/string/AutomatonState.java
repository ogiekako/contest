package net.ogiekako.algorithm.string;

import net.ogiekako.algorithm.dataStructure.persistent.PersistentLinkedList;

public class AutomatonState {
    private int id;
    AutomatonState[] nextStates;
    AutomatonState failureLink;
    PersistentLinkedList<String> matchListFromLonger;

    AutomatonState(int letterCount, int id) {
        nextStates = new AutomatonState[letterCount];
        this.id = id;
    }

    public int getIndex() {
        return id;
    }

    public AutomatonState next(int letterId) {
        AutomatonState v = this;
        while (v.nextStates[letterId] == null) v = v.failureLink;
        v = v.nextStates[letterId];
        return v;
    }

    public PersistentLinkedList<String> getMatchedFromLonger() {
        return matchListFromLonger;
    }
}
