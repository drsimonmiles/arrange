package uk.ac.kcl.inf.arrange;

import java.util.LinkedList;

public class Tuple extends LinkedList<Atomic> implements Variable {
    Tuple (Atomic... elements) {
        for (Atomic element : elements) {
            add (element);
        }
    }
}
