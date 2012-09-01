package uk.ac.kcl.inf.arrange;

import java.util.LinkedList;

/**
 * An unordered set of variables. The variables may be atomic or composite.
 */
public class VarSet extends LinkedList<Variable> implements Variable {
    /**
     * Create a variables set with the given elements.
     */
    public VarSet (Variable... elements) {
        for (Variable element : elements) {
            add (element);
        }
    }
}
