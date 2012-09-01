package uk.ac.kcl.inf.arrange;

import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;

/**
 * Represents an integer variable or expression result.
 */
public class Atomic implements Variable {
    /** The Choco expression variable on which this is based */
    private IntegerExpressionVariable _basis;
    /** The range of values the variable can take */
    private Range _range;

    /**
     * Create a new Atomic variable based on the given expression and with values
     * in the given range.
     */
    Atomic (IntegerExpressionVariable basis, Range range) {
        _basis = basis;
        _range = range;
    }
    
    /**
     * Create a new Atomic variable based on the given expression and with values
     * in the given range.
     */
    Atomic (IntegerExpressionVariable basis, int minimum, int maximum) {
        this (basis, new Range (minimum, maximum));
    }
    
    /**
     * Returns the Choco variable on which this is based.
     */
    IntegerExpressionVariable getBasis () {
        return _basis;
    }

    /**
     * Returns the Choco variable on which this is based, where the latter is
     * strictly a variable and not the result of an expression over variables.
     */
    IntegerVariable getBasisIV () {
        return (IntegerVariable) _basis;
    }    

    /**
     * Returns the range of values the variable can take.
     */
    public Range getRange () {
        return _range;
    }
}
