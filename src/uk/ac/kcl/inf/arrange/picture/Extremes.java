package uk.ac.kcl.inf.arrange.picture;

import uk.ac.kcl.inf.arrange.Atomic;

class Extremes {
    Atomic _y;
    Atomic _xMinimum;
    Atomic _xMaximum;
    
    Extremes (Atomic y, Atomic xMinimum, Atomic xMaximum) {
        _y = y;
        _xMinimum = xMinimum;
        _xMaximum = xMaximum;
    }
}
