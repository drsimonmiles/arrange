package uk.ac.kcl.inf.arrange.picture;

/**
 * Position of perpendicular is at its connection with connection1. Length can
 * be negative meaning left/up.
 */
public class Perpendicular implements Visible {
    public final boolean _isVertical;
    
    public Perpendicular (boolean isVertical) {
        _isVertical = isVertical;
    }
}