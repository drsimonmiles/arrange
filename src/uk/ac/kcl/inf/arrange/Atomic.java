package uk.ac.kcl.inf.arrange;

import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;

public class Atomic implements Variable {
    private IntegerExpressionVariable _basis;
    private Range _range;
    
    Atomic (IntegerExpressionVariable basis, Range range) {
        _basis = basis;
        _range = range;
    }
    
    Atomic (IntegerExpressionVariable basis, int minimum, int maximum) {
        this (basis, new Range (minimum, maximum));
    }
    
    IntegerExpressionVariable getBasis () {
        return _basis;
    }

    IntegerVariable getBasisIV () {
        return (IntegerVariable) _basis;
    }    
    
    public Range getRange () {
        return _range;
    }
}
