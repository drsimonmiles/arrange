package uk.ac.kcl.inf.arrange.timetable;

import uk.ac.kcl.inf.arrange.VarSet;

public class Test {
    public static void main (String[] arguments) {
        TimetableProblem problem = new TimetableProblem (5, 9, 9);
        VarSet session1 = problem.session (2);
        VarSet session2 = problem.session (3);
        VarSet session3 = problem.session (2);
        VarSet session4 = problem.session (4);
        Resource lecturer1 = new Resource ().unavailable (0, 0).unavailable (1, 0).
                unavailable (2, 0).unavailable (3, 0).unavailable (4, 0).
                usedIn (session1, session2);
        Resource lecturer2 = new Resource ().usedIn (session3);
        Resource lecturer3 = new Resource ().usedIn (session4);
        Resource students1 = new Resource ().unavailable (2, 4).unavailable (2, 5).
                unavailable (2, 6).unavailable (2, 7).unavailable (2, 8).
                usedIn (session1, session3);
        Resource students2 = new Resource ().unavailable (2, 4).unavailable (2, 5).
                unavailable (2, 6).unavailable (2, 7).unavailable (2, 8).
                usedIn (session2, session4);
        VarSet lecturer1times = problem.resource (lecturer1);
        VarSet lecturer2times = problem.resource (lecturer2);
        VarSet lecturer3times = problem.resource (lecturer3);
        VarSet students1times = problem.resource (students1);
        VarSet students2times = problem.resource (students2);
        
        problem.limitUse (lecturer1, 0, 4);
        problem.limitUse (lecturer1, 1, 4);
        problem.limitUse (lecturer1, 2, 4);
        problem.limitUse (lecturer1, 3, 4);
        problem.limitUse (lecturer1, 4, 4);
        
        problem.solve ();

        System.out.println (problem.valuesString (problem.allSessions ()));
    }
}