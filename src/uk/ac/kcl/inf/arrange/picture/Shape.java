package uk.ac.kcl.inf.arrange.picture;

public interface Shape extends Visible {
    int extentAbovePosition ();
    int extentBelowPosition ();
    int extentLeftOfPosition ();
    int extentRightOfPosition ();
    int getLeftExtremity (int yOffset);
    int getRightExtremity (int yOffset);
}
