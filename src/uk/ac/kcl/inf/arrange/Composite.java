package uk.ac.kcl.inf.arrange;

import choco.Choco;
import choco.kernel.model.constraints.Constraint;
import java.util.LinkedList;
import java.util.List;

public class Composite extends Constraints {
    private final List<Constraint> _list;
    private final boolean _isDisjunction;
    
    Composite (Constraints parent, boolean isDisjunction) {
        super (parent);
        _list = new LinkedList<Constraint> ();
        _isDisjunction = isDisjunction;
    }
    
    @Override
    protected void addConstraint (Constraint constraint) {
        _list.add (constraint);
    }
    
    protected Constraint createComposition () {
        if (_isDisjunction) {
            return Choco.or (_list.toArray (new Constraint[0]));
        } else {
            return Choco.and (_list.toArray (new Constraint[0]));
        }
    }
}
