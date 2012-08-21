package uk.ac.kcl.inf.arrange;

import java.util.LinkedList;

public class VarSet extends LinkedList<Variable> implements Variable {
    public VarSet (Variable... elements) {
        for (Variable element : elements) {
            add (element);
        }
    }
}
