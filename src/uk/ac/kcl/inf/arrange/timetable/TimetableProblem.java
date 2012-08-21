package uk.ac.kcl.inf.arrange.timetable;

import java.util.LinkedList;
import java.util.List;
import uk.ac.kcl.inf.arrange.Atomic;
import uk.ac.kcl.inf.arrange.Problem;
import uk.ac.kcl.inf.arrange.Range;
import uk.ac.kcl.inf.arrange.VarSet;

public class TimetableProblem extends Problem {
    public final Range _days, _slots;
    public final List<VarSet> _sessions;
    
    public TimetableProblem (int days, int slotsPerDay, int firstSlot) {
        _days = range (0, days - 1);
        _slots = range (firstSlot, firstSlot + slotsPerDay - 1);
        _sessions = new LinkedList<> ();
    }
   
    public VarSet allSessions () {
        return set (_sessions);
    }
    
    public Atomic countUse (Resource resource, int day) {
        return count (element (resource._used, 0), day);
    }
    
    public void limitUse (Resource resource, int day, int maxUse) {
        leq (countUse (resource, day), maxUse);
    }
    
    public VarSet resource (Resource resource) {
        VarSet unavailable = new VarSet ();
        
        for (int index = 0; index < resource._unavailableDays.size (); index += 1) {
            unavailable.add (tuple (resource._unavailableDays.get (index), resource._unavailableSlots.get (index)));
        }
        
        return disjoint (unavailable, resource._used);
    }
    
    public VarSet session (int length) {
        Atomic day = variable (_days);
        VarSet hours = sequence (length, _slots);
        VarSet session = combinations (day, hours);
        
        _sessions.add (session);
        
        return session;
    }
}
