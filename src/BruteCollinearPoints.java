import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<SegmentNode> segmentNodes;

    private class SegmentNode {
        private Point p;
        private Point q;
        private double slope;

    }


    public BruteCollinearPoints(Point[] points) {
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

        segmentNodes = new ArrayList<>();

        for (int i = 0; i < numberOfPoints - 3; i++) {
            for (int j = i + 1; j < numberOfPoints - 2; j++) {
                double slope1 = copyOfPoints[i].slopeTo(copyOfPoints[j]);
                for (int k = j + 1; k < numberOfPoints - 1; k++) {
                    double slope2 = copyOfPoints[j].slopeTo(copyOfPoints[k]);
                    if (Double.compare(slope1, slope2) != 0) continue;
                    for (int l = k + 1; l < numberOfPoints; l++) {
                        double slope3 = copyOfPoints[k].slopeTo(copyOfPoints[l]);
                        if (Double.compare(slope3, slope2) != 0) continue;
                        mark(copyOfPoints[i], copyOfPoints[l], slope3);

                    }
                }
            }
        }
    }

    private void mark(Point a, Point b, Double slope) {
        if (segmentNodes.size() == 0) {
            SegmentNode segmentNode = new SegmentNode();
            segmentNode.p = a;
            segmentNode.q = b;
            segmentNode.slope = slope;
            segmentNodes.add(segmentNode);
        } else {
            SegmentNode last = segmentNodes.get(segmentNodes.size() - 1);
            if (Double.compare(last.slope, slope) != 0) {
                SegmentNode segmentNode = new SegmentNode();
                segmentNode.p = a;
                segmentNode.q = b;
                segmentNode.slope = slope;
                segmentNodes.add(segmentNode);
            } else {
                last.q = b;
            }
        }

    }

    public int numberOfSegments() {
        return segmentNodes.size();
    }

    public LineSegment[] segments() {
        int sizeOfSegmentNodes = numberOfSegments();
        LineSegment[] lines = new LineSegment[sizeOfSegmentNodes];
        for (int i = 0; i < sizeOfSegmentNodes; i++) {
            SegmentNode segmentNode = segmentNodes.get(i);
            LineSegment line = new LineSegment(segmentNode.p, segmentNode.q);
            lines[i] = line;
        }
        return lines;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
