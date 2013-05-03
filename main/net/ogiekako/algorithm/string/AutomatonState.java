package net.ogiekako.algorithm.string;

import net.ogiekako.algorithm.dataStructure.persistent.PersistentLinkedList;

/**
* Created by IntelliJ IDEA.
* User: ogiekako
* Date: 12/07/05
* Time: 16:28
* To change this template use File | Settings | File Templates.
*/
public class AutomatonState {
    private int id;
    AutomatonState[] nextStates;
    AutomatonState failureLink;
    PersistentLinkedList<String> matchListFromLonger;

    AutomatonState(int letterCount, int id) {
        nextStates= new AutomatonState[letterCount];
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

    public PersistentLinkedList<String> getMatchedFromLonger(){
        return matchListFromLonger;
    }
}
