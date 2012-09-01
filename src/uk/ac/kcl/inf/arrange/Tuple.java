package uk.ac.kcl.inf.arrange;

import java.util.LinkedList;

/**
 * An ordered set of atomic variables.
 */
public class Tuple extends LinkedList<Atomic> implements Variable {
    /**
     * Create a tuple with the given atomic elements.
     */
    Tuple (Atomic... elements) {
        for (Atomic element : elements) {
            add (element);
        }
    }
}
