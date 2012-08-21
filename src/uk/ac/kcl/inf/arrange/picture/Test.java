package uk.ac.kcl.inf.arrange.picture;

import java.util.List;

public class Test {
    public static void main (String[] arguments) {
        int width = 17, height = 17;
        PictureProblem problem = new PictureProblem (width, height);
        Rectangle rectangle1 = new Rectangle (3, 3);
        Rectangle rectangle2 = new Rectangle (2, 2);
        Rectangle rectangle3 = new Rectangle (2, 3);
        Circle circle1 = new Circle (17);

        problem.nonOverlapping (rectangle1, rectangle2, rectangle3, circle1);
        
        System.out.println (problem.solve ());
        
//        System.out.println (problem.valuesString (problem.getPosition (rectangle1)));
        //System.out.println (problem.valuesString (problem.getPosition (rectangle2)));
        //System.out.println (problem.valuesString (problem.getPosition (rectangle3)));
//        System.out.println (problem.valuesString (problem.getPosition (circle1)));
        
        for (int y = 0; y < width; y += 1) {
            for (int x = 0; x < height; x += 1) {
                if (covers (x, y, problem, rectangle1, rectangle2, rectangle3, circle1)) {
                    System.out.print ("#");
                } else {
                    System.out.print (".");
                }
            }
            System.out.println ();
        }
    }
    
    public static boolean covers (int x, int y, PictureProblem problem, Shape... shapes) {
        for (Shape shape : shapes) {
            if (covers (x, y, problem, shape)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean covers (int x, int y, PictureProblem problem, Shape shape) {
        List<Integer> position = problem.values (problem.getPosition (shape));
        int xPosition = position.get (0);
        int yOffset = y - position.get (1);
        
        return yOffset >= -shape.extentAbovePosition () && yOffset <= shape.extentBelowPosition () &&
               shape.getLeftExtremity (yOffset) + xPosition <= x &&
               shape.getRightExtremity (yOffset) + xPosition >= x;
    }
}
