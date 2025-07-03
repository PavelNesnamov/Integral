package ait.integral;

import java.util.function.Function;

public class SumRectangles implements Runnable {
    private double a; // lower bound of integration
    private double b; // upper bound of integration
    private Function<Double, Double> function; // function to integrate
    private int nParts; // total number of rectangles
    private int nTasks; // total number of tasks
    private int taskIndex; // index of current task
    private double result; // result of integration for this task's portion

    public SumRectangles(double a, double b, Function<Double, Double> function, int nParts, int nTasks, int taskIndex) {
        this.a = a;
        this.b = b;
        this.function = function;
        this.nParts = nParts;
        this.nTasks = nTasks;
        this.taskIndex = taskIndex;
    }

    @Override
    public void run() {
        // Calculate the width of each rectangle
        double width = (b - a) / nParts;
        
        // Calculate the number of rectangles this task should process
        int partsPerTask = nParts / nTasks;
        
        // Calculate the starting and ending indices for this task
        int startIndex = taskIndex * partsPerTask;
        int endIndex = (taskIndex == nTasks - 1) ? nParts : (taskIndex + 1) * partsPerTask;
        
        // Calculate the sum of the areas of the rectangles
        double sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            // Calculate the x-coordinate of the midpoint of the rectangle
            double x = a + (i + 0.5) * width;
            
            // Calculate the height of the rectangle (function value at midpoint)
            double height = function.apply(x);
            
            // Add the area of the rectangle to the sum
            sum += height * width;
        }
        
        // Store the result
        result = sum;
    }

    public double getResult() {
        return result;
    }
}