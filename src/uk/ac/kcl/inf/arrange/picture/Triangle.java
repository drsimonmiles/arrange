package uk.ac.kcl.inf.arrange.picture;

public class Triangle implements Shape {

    public final int _height;
    public final int _width;
    public final boolean _pointUp;
    
    public Triangle (int height, int width, boolean pointUp) {
        _height = height;
        _width = width;
        _pointUp = pointUp;
    }
    
    @Override
    public int extentAbovePosition () {
        if (_pointUp) {
            return _height - 1;
        } else {
            return 0;
        }
    }

    @Override
    public int extentBelowPosition () {
        if (_pointUp) {
            return 0;
        } else {
            return _height - 1;
        }
    }

    @Override
    public int extentLeftOfPosition () {
        if (_pointUp) {
            return 0;
        } else {
            return _width - 1;
        }
    }

    @Override
    public int extentRightOfPosition () {
        if (_pointUp) {
            return _width - 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getLeftExtremity (int yOffset) {
        if (_pointUp) {
            return 0;
        } else {
            // yOffset is negative, so add instead of minus
            return (_height + yOffset) * _width / _height;
        }
    }

    @Override
    public int getRightExtremity (int yOffset) {
        if (_pointUp) {
            // yOffset is negative, so add instead of minus
            return (_height + yOffset) * _width / _height;
        } else {
            return _width - 1;
        }
    }

}
