package uk.ac.kcl.inf.arrange;

/**
 * A range of values that have significance for a given variable.
 */
public class Range {
    private int _minimum;
    private int _maximum;
    
    public Range (int minimum, int maximum) {
        _minimum = minimum;
        _maximum = maximum;
    }
    
    public int getLength () {
        return _maximum - _maximum + 1;
    }
    
    public int getMaximum () {
        return _maximum;
    }

    public int getMinimum () {
        return _minimum;
    }
    
    public int[] toIntArray () {
        int[] array = new int[_maximum - _minimum + 1];
        
        for (int value = _minimum; value <= _maximum; value += 1) {
            array[value - _minimum] = value;
        }
        
        return array;
    }
}
