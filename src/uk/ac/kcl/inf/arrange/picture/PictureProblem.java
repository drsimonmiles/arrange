package uk.ac.kcl.inf.arrange.picture;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.ac.kcl.inf.arrange.Atomic;
import uk.ac.kcl.inf.arrange.Problem;
import uk.ac.kcl.inf.arrange.Range;
import uk.ac.kcl.inf.arrange.Tuple;

public class PictureProblem extends Problem {
    public final Range _xValues, _yValues;
    private final Map<Visible, Tuple> _positions;
    private final Map<Shape, List<Extremes>> _extremes;
    private final Map<Perpendicular, Atomic> _lengths;
    private final Map<Perpendicular, Visible> _starts;
    private final Map<Perpendicular, Visible> _ends;

    public PictureProblem (int width, int height) {
        _xValues = range (0, width - 1);
        _yValues = range (0, height - 1);
        _positions = new HashMap<> ();
        _extremes = new HashMap<> ();
        _lengths = new HashMap<> ();
        _starts = new HashMap<> ();
        _ends = new HashMap<> ();
    }

    public void connect (Perpendicular line, Visible from, Visible to) {
        _starts.put (line, from);
        _ends.put (line, to);
        eq (getPosition (line)
    }
    
    private List<Extremes> getExtremities (Shape shape) {
        List<Extremes> extremes = _extremes.get (shape);
        Tuple position;
        int xOffsetLeft, xOffsetRight;
        Atomic left, right, y;

        if (extremes == null) {
            extremes = new LinkedList<> ();
            position = getPosition (shape);
            for (int yOffset = -shape.extentAbovePosition (); yOffset <= shape.extentBelowPosition (); yOffset += 1) {
                xOffsetLeft = shape.getLeftExtremity (yOffset);
                xOffsetRight = shape.getRightExtremity (yOffset);
                left = sum (position.get (0), xOffsetLeft);
                right = sum (position.get (0), xOffsetRight);
                y = sum (position.get (1), yOffset);
                extremes.add (new Extremes (y, left, right));
            }
            _extremes.put (shape, extremes);
        }

        return extremes;
    }

    private Extremes getExtremities (Shape shape, int yOffset) {
        return getExtremities (shape).get (yOffset + shape.extentAbovePosition ());
    }

    public Atomic getLength (Perpendicular line) {
        Atomic length = _lengths.get (line);
        
        if (length == null) {
            if (line._isVertical) {
                length = variable (-_yValues.getLength (), _yValues.getLength ());
            } else {
                length = variable (-_xValues.getLength (), _xValues.getLength ());
            }
            _lengths.put (line, length);
        }
        
        return length;
    }
    
    public Tuple getPosition (Visible visible) {
        Tuple position = _positions.get (visible);
        int minimumX = _xValues.getMinimum (), maximumX = _xValues.getMaximum ();
        int minimumY = _yValues.getMinimum (), maximumY = _yValues.getMaximum ();
        Shape shape;

        if (position == null) {
            if (visible instanceof Shape) {
                shape = (Shape) visible;
                minimumX += shape.extentLeftOfPosition ();
                maximumX -= shape.extentRightOfPosition ();
                minimumY += shape.extentAbovePosition ();
                maximumY -= shape.extentBelowPosition ();
            }
            position = tuple (variable (minimumX, maximumX), variable (minimumY, maximumY));
            _positions.put (visible, position);
        }

        return position;
    }

    public void nonOverlapping (Shape... shapes) {
        for (int index1 = 0; index1 < shapes.length; index1 += 1) {
            for (int index2 = index1 + 1; index2 < shapes.length; index2 += 1) {
                nonOverlappingPair (shapes[index1], shapes[index2]);
            }
        }
    }

    private void nonOverlappingPair (Shape shapeA, Shape shapeB) {
        // For all extremities of A, for each extremity of B, either different Y, 
        // or rightmost A less than leftmost B or leftmost A more than rightmost B
        for (Extremes extremesA : getExtremities (shapeA)) {
            for (Extremes extremesB : getExtremities (shapeB)) {
                or ().neq (extremesA._y, extremesB._y).
                        lt (extremesA._xMaximum, extremesB._xMinimum).
                        lt (extremesB._xMaximum, extremesA._xMinimum).
                        end ();
            }
        }
    }
}
