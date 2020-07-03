import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        int numberOfPoints = points.length;

        for (int i = 0; i < numberOfPoints; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < numberOfPoints; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        if (numberOfPoints < 4) return;

        Point[] copyOfPoints = Arrays.copyOf(points, numberOfPoints);
        Arrays.sort(copyOfPoints);

        lineSegments = new ArrayList<>();
        Point[] other = new Point[numberOfPoints - 1];

        for (int i = 0; i < numberOfPoints; i++) {

            Point current = copyOfPoints[i];
            for (int t = 0; t < numberOfPoints; t++) {
                if (t < i)
                    other[t] = copyOfPoints[t];
                else if (t > i)
                    other[t - 1] = copyOfPoints[t];
            }
            Arrays.sort(other, current.slopeOrder());
            System.out.println(Arrays.toString(other));

        }

        }



    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {

        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}