package uk.ac.kcl.inf.arrange;

import choco.Choco;
import choco.kernel.model.constraints.Constraint;

public abstract class Constraints {
    private final Constraints _parent;
    
    Constraints (Constraints parent) {
        _parent = parent;
    }
    
    protected abstract void addConstraint (Constraint constraint);
    
    public Composite and () {
        return new Composite (this, false);
    }

    protected Constraint createComposition () {
        return null;
    }
    
    public void end () {
        Constraint composition;
        
        if (_parent != null) {
            composition = createComposition ();
            if (composition != null) {
                _parent.addConstraint (composition);
            }
        }
    }
    
    public Constraints leq (Atomic variable1, Atomic variable2) {
        addConstraint (Choco.leq (variable1.getBasis (), variable2.getBasis ()));
        return this;
    }

    public Constraints leq (Atomic variable, int maximum) {
        addConstraint (Choco.leq (variable.getBasis (), maximum));
        return this;
    }

    public Constraints lt (Atomic variable1, Atomic variable2) {
        addConstraint (Choco.lt (variable1.getBasis (), variable2.getBasis ()));
        return this;
    }

    public Constraints neq (Atomic variable1, Atomic variable2) {
        addConstraint (Choco.neq (variable1.getBasis (), variable2.getBasis ()));
        return this;
    }
    
    public Composite or () {
        return new Composite (this, true);
    }
}
