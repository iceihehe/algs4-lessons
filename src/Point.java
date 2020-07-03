/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        if (y < o.y || (y == o.y && x < o.x)) return -1;
        if (y == o.y && x == o.x) return 0;
        return 1;
    }

    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }

        return (double) (that.y - y) / (double) (that.x - x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            double slope1 = slopeTo(p);
            double slope2 = slopeTo(q);
            if (slope1 < slope2) {
                return -1;
            } else if (slope1 > slope2) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p = new Point(1, 2);
        Point q = new Point(0, -1);
        Point r = new Point(0, -3);
        System.out.println(p.compareTo(q));
        System.out.println(p.slopeTo(q));
        Point[] points = new Point[3];
        points[0] = p;
        points[1] = r;
        points[2] = q;
        Arrays.sort(points, p.slopeOrder());
        System.out.println(Arrays.toString(points));
    }


}