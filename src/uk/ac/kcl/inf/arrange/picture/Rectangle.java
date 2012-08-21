package uk.ac.kcl.inf.arrange.picture;

public class Rectangle implements Shape {
    public final int _width;
    public final int _height;
    
    public Rectangle (int width, int height) {
        _width = width;
        _height = height;
    }

    @Override
    public int extentAbovePosition () {
        return 0;
    }

    @Override
    public int extentBelowPosition () {
        return _height - 1;
    }

    @Override
    public int extentLeftOfPosition () {
        return 0;
    }

    @Override
    public int extentRightOfPosition () {
        return _width - 1;
    }

    public int getLeftExtremity (int yOffset) {
        //System.out.println ("R Leftmost at " + yOffset + " is 0");
        return 0;
    }

    public int getRightExtremity (int yOffset) {
        //System.out.println ("R Rightmost at " + yOffset + " is " + (_width - 1));
        return _width - 1;
    }
}
