import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final Point[] points;
    private final LineSegment[] cached;


    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        this.points = Arrays.copyOf(points, points.length);
        for (Point point : this.points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(this.points);
        for (int i = 0; i < this.points.length; i++) {
            if (i > 0 && Double.compare(this.points[i].slopeTo(this.points[i - 1]), Double.NEGATIVE_INFINITY) == 0) {
                throw new IllegalArgumentException();
            }
        }
        cached = cache();
        }



    public int numberOfSegments() {
        return cached.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(cached, cached.length);
    }
    private LineSegment[] cache() {
        ArrayList<LineSegment> list = new ArrayList<>();
        Arrays.sort(points);
        for (Point p: points) {
            Point[] otherPoints = Arrays.copyOf(points, points.length);
            if (otherPoints.length < 4) {
                continue;
            }
            Arrays.sort(otherPoints, p.slopeOrder());
            int begin = 1;
            int end = 1;
            double last = p.slopeTo(otherPoints[end]);
            for (int j = 2; j < otherPoints.length; j++) {
                double slope = p.slopeTo(otherPoints[j]);
                if (Double.compare(last, slope) != 0) {
                    if (end - begin >= 2) {
                        if (p.compareTo(otherPoints[begin]) < 0) {

                            list.add(new LineSegment(p, otherPoints[end]));
                        }
                    }
                    last = slope;
                    begin = j;
                }
                end = j;
            }
            if (end - begin >= 2) {
                if (p.compareTo(otherPoints[begin]) < 0) {
                    list.add(new LineSegment(p, otherPoints[end]));
                }
            }
        }
        LineSegment[] segments = new LineSegment[list.size()];
        return list.toArray(segments);
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
//        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}