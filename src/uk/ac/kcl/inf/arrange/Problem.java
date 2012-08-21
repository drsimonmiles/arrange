package uk.ac.kcl.inf.arrange;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Problem extends Constraints {
    private final CPModel _model;
    private CPSolver _solver;
    private int _varCount;

    public Problem () {
        super (null);
        _model = new CPModel ();
        _solver = null;
        _varCount = 0;
    }
    
    protected void addConstraint (Constraint constraint) {
        _model.addConstraint (constraint);
    }

    private IntegerExpressionVariable[] array (Collection<IntegerExpressionVariable> list) {
        return list.toArray (new IntegerExpressionVariable[0]);
    }

    private IntegerVariable[] arrayIV (Collection<IntegerVariable> list) {
        return list.toArray (new IntegerVariable[0]);
    }
    
    private IntegerExpressionVariable atom (Variable atomicVariable) {
        return ((Atomic) atomicVariable).getBasis ();
    }

    private IntegerVariable atomIV (Variable atomicVariable) {
        return ((Atomic) atomicVariable).getBasisIV ();
    }

    private IntegerExpressionVariable atom (Tuple tuple, int element) {
        return atom (tuple.get (element));
    }    
    
    private List<IntegerExpressionVariable> atoms (VarSet atomicVariables) {
        List<IntegerExpressionVariable> atoms = new LinkedList<> ();

        for (Variable atom : atomicVariables) {
            atoms.add (atom (atom));
        }

        return atoms;
    }

    private List<IntegerVariable> atomsIV (VarSet atomicVariables) {
        List<IntegerVariable> atoms = new LinkedList<> ();

        for (Variable atom : atomicVariables) {
            atoms.add (atomIV (atom));
        }

        return atoms;
    }
    
    public VarSet combinations (Variable set1, Variable set2) {
        VarSet combinations = new VarSet ();
        VarSet members1, members2;

        if (set1 instanceof VarSet) {
            members1 = members ((VarSet) set1);
        } else {
            members1 = new VarSet (set1);
        }
        if (set2 instanceof VarSet) {
            members2 = members ((VarSet) set2);
        } else {
            members2 = new VarSet (set2);
        }

        for (Variable var1 : members1) {
            for (Variable var2 : members2) {
                combinations.add (new Tuple ((Atomic) var1, (Atomic) var2));
            }
        }

        return combinations;
    }

    public Atomic constant (int value) {
        return new Atomic (Choco.constant (value), range (value, value));
    }

    public Atomic count (VarSet set, int value) {
        return count (set, value, value);
    }

    public Atomic count (VarSet set, int minimum, int maximum) {
        return count (set, range (minimum, maximum));
    }
    
    public Atomic count (VarSet set, Range inRange) {
        VarSet members = members (set);
        Atomic count = variable (0, set.size ());
        
        _model.addConstraint (Choco.among (count.getBasisIV (), arrayIV (atomsIV (members)), inRange.toIntArray ()));
        
        return count;
    }
    
    private IntegerVariable create (int minimum, int maximum) {
        return Choco.makeIntVar (createName (), minimum, maximum);
    }

    private IntegerVariable create (Range range) {
        return create (range.getMinimum (), range.getMaximum ());
    }

    private String createName () {
        _varCount += 1;
        return "v" + _varCount;
    }

    public VarSet disjoint (Variable... variables) {
        VarSet members = members (variables);
        Tuple tuple1, tuple2;
        int size;
        Constraint[] parts;

        if (members.size () > 0) {
            if (members.get (0) instanceof Atomic) {
                _model.addConstraint (Choco.allDifferent (arrayIV (atomsIV (members))));
            }
            if (members.get (0) instanceof Tuple) {
                tuple1 = ((Tuple) members.get (0));
                size = tuple1.size ();
                parts = new Constraint[size];
                for (int index1 = 0; index1 < members.size (); index1 += 1) {
                    tuple1 = ((Tuple) members.get (index1));
                    for (int index2 = index1 + 1; index2 < members.size (); index2 += 1) {
                        tuple2 = ((Tuple) members.get (index2));
                        for (int part = 0; part < size; part += 1) {
                            parts[part] = Choco.neq (atom (tuple1, part), atom (tuple2, part));
                        }
                        _model.addConstraint (Choco.or (parts));
                    }
                }
            }
        }

        return members;
    }

    public VarSet element (VarSet tuples, int part) {
        VarSet elements = new VarSet ();
        VarSet members = members (tuples);
        
        for (Variable tuple : members) {
            elements.add (((Tuple) tuple).get (part));
        }
        
        return elements;
    }
        
    private VarSet members (Variable... variables) {
        return members (new VarSet (variables));
    }

    private VarSet members (VarSet variables) {
        return members (0, new VarSet (), variables);
    }

    private VarSet members (int from, VarSet current, VarSet variables) {
        Variable next;

        if (from >= variables.size ()) {
            return current;
        }
        next = variables.get (from);
        if (next instanceof VarSet) {
            current.addAll (members ((VarSet) next));
        }
        if (next instanceof Tuple || next instanceof Atomic) {
            current.add (next);
        }
        return members (from + 1, current, variables);
    }
    
    public Range range (int start, int end) {
        return new Range (start, end);
    }

    public VarSet sequence (int count, Range range, int step) {
        IntegerVariable constant = Choco.constant (step);
        VarSet variables = new VarSet ();
        Variable previous = null, next;

        while (count > 0) {
            next = variable (range);
            variables.add (next);
            if (previous != null) {
                _model.addConstraint (Choco.eq (atom (next), Choco.sum (atom (previous), constant)));
            }
            previous = next;
            count -= 1;
        }

        return variables;
    }

    public VarSet sequence (int count, Range range) {
        return sequence (count, range, 1);
    }

    public VarSet set (List<? extends Variable> elements) {
        VarSet set = new VarSet ();

        for (Object element : elements) {
            if (element instanceof Variable) {
                set.add ((Variable) element);
            }
            if (element instanceof Integer) {
                set.add (constant ((Integer) element));
            }
        }

        return set;
    }
    
    public VarSet set (Variable... elements) {
        return set (Arrays.asList (elements));
    }
    
    public boolean solve () {
        _solver = new CPSolver ();
        _solver.read (_model);
        return _solver.solve ();
    }

    public Atomic sum (Atomic variable, int addition) {
        int minimum = variable.getRange ().getMinimum () + addition;
        int maximum = variable.getRange ().getMaximum () + addition;
        
        return new Atomic (Choco.sum (variable.getBasis (), constant (addition).getBasis ()), minimum, maximum);
    }
    
    public Atomic sum (Variable... variables) {
        VarSet members = members (variables);
        IntegerExpressionVariable[] array = array (atoms (members));
        int minimum = 0, maximum = 0;
        
        for (Variable variable : members) {
            minimum += ((Atomic) variable).getRange ().getMinimum ();
            maximum += ((Atomic) variable).getRange ().getMaximum ();
        }

        return new Atomic (Choco.sum (array), minimum, maximum);
    }
    
    public Tuple tuple (Object... elements) {
        return tuple (Arrays.asList (elements));
    }

    public Tuple tuple (List<Object> elements) {
        Tuple tuple = new Tuple ();

        for (Object element : elements) {
            if (element instanceof Atomic) {
                tuple.add ((Atomic) element);
            }
            if (element instanceof Tuple) {
                tuple.addAll ((Tuple) element);
            }
            if (element instanceof VarSet) {
                tuple.addAll (tuple ((VarSet) element));
            }
            if (element instanceof Integer) {
                tuple.add (constant ((Integer) element));
            }
        }

        return tuple;
    }

    public VarSet union (Variable... sets) {
        return members (sets);
    }
    
    public int value (Atomic atomic) {
        return _solver.getVar (atomic.getBasisIV ()).getVal ();
    }

    public List<Integer> values (Variable variable) {
        List<Integer> values = new LinkedList<> ();
        
        if (variable instanceof Atomic) {
            return Collections.singletonList (value ((Atomic) variable));
        }
        if (variable instanceof Tuple) {
            for (Atomic atomic : (Tuple) variable) {
                values.add (value (atomic));
            }
        }
        if (variable instanceof VarSet) {
            for (Variable element : (VarSet) variable) {
                values.addAll (values (element));
            }
        }
        
        return values;
    }
    
    public String valueString (Atomic atomic) {
        return String.valueOf (value (atomic));
    }

    public String valuesString (Variable variable) {
        StringBuilder text = new StringBuilder ();
        boolean first = true;
        
        if (variable instanceof Atomic) {
            return valueString ((Atomic) variable);
        }
        if (variable instanceof Tuple) {
            text.append ("(");
            for (Atomic atomic : (Tuple) variable) {
                if (!first) {
                    text.append (", ");
                }
                text.append (valueString ((Atomic) atomic));
                first = false;
            }
            text.append (")");
        }
        if (variable instanceof VarSet) {
            text.append ("[");
            for (Variable element : (VarSet) variable) {
                if (!first) {
                    text.append (", ");
                }
                text.append (valuesString (element));
                first = false;
            }
            text.append ("]");
        }
        
        return text.toString ();
    }
    
    public Atomic variable (int minimum, int maximum) {
        return variable (range (minimum, maximum));
    }
    
    public Atomic variable (Range range) {
        return new Atomic (create (range), range);
    }
}
