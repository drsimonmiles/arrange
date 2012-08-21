package uk.ac.kcl.inf.arrange.timetable;

import java.util.LinkedList;
import java.util.List;
import uk.ac.kcl.inf.arrange.VarSet;

public class Resource {
    final List<Integer> _unavailableDays;
    final List<Integer> _unavailableSlots;
    final VarSet _used;
    
    public Resource () {
        _unavailableDays = new LinkedList<> ();
        _unavailableSlots = new LinkedList<> ();
        _used = new VarSet ();
    }
    
    public VarSet getUsed () {
        return _used;
    }
    
    public Resource unavailable (int day, int slot) {
        _unavailableDays.add (day);
        _unavailableSlots.add (slot);
        return this;
    }
    
    public Resource usedIn (VarSet... sessions) {
        for (VarSet session : sessions) {
            _used.add (session);
        }
        return this;
    }
}
