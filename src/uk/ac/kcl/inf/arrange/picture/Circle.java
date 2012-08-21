package uk.ac.kcl.inf.arrange.picture;

public class Circle implements Shape {
    public final int _diameter;
    private final int _radius;
    private final boolean _evenDiameter;
    
    public Circle (int diameter) {
        _diameter = diameter;
        if (diameter % 2 == 0) {
            _evenDiameter = true;
            _radius = _diameter / 2;
        } else {
            _evenDiameter = false;
            _radius = (_diameter - 1) / 2;
        }
    }

    @Override
    public int extentAbovePosition () {
        if (_evenDiameter) {
            return _radius - 1;
        } else {
            return _radius;
        }
    }

    @Override
    public int extentBelowPosition () {
        return _radius;
    }

    @Override
    public int extentLeftOfPosition () {
        if (_evenDiameter) {
            return _radius - 1;
        } else {
            return _radius;
        }
    }

    @Override
    public int extentRightOfPosition () {
        return _radius;
    }

    @Override
    public int getLeftExtremity (int yOffset) {
        //System.out.println ("Leftmost at " + yOffset + " is " + ((int) -Math.sqrt ((_radius * _radius) - (yOffset * yOffset))));
        return (int) -Math.sqrt ((_radius * _radius) - (yOffset * yOffset));
    }

    @Override
    public int getRightExtremity (int yOffset) {
        //System.out.println ("Rightmost at " + yOffset + " is " + ((int) Math.sqrt ((_radius * _radius) - (yOffset * yOffset))));
        return (int) Math.sqrt ((_radius * _radius) - (yOffset * yOffset));
    }
    
    
}
