/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points or more
     */
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] sortedP = points.clone();
        Arrays.sort(sortedP);
        checkDuplicate(sortedP);

        final int length = sortedP.length;
        final List<LineSegment> segmentList = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            // sort array based on slope with point p
            Point p = sortedP[i];
            Point[] slopeSortedP = sortedP.clone();
            Arrays.sort(slopeSortedP, p.slopeOrder());

            // loop through slopes
            int j = 1;
            while (j < length) {
                List<Point> possibleP = new LinkedList<>();
                final double targetSlope = p.slopeTo(slopeSortedP[j]);
                do {
                    // add slopes to possible points until first different slope
                    possibleP.add(slopeSortedP[j++]);
                } while (j < length && p.slopeTo(slopeSortedP[j]) == targetSlope);

                // There is a maximal line segment if:
                // 1. three or more points added to list
                // 2. point p is the smallest of the list
                if (possibleP.size() >= 3 && p.compareTo(possibleP.get(0)) < 0) {
                    Point last = possibleP.get(possibleP.size() - 1);
                    LineSegment segment = new LineSegment(p, last);
                    segmentList.add(segment);
                }
            }
        }
        lineSegments = segmentList.toArray(new LineSegment[0]);
    }

    /**
     * gets the number of line segments
     *
     * @return the number of segments
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * The line segments.
     *
     * @return the array of line segments
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }


    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The array entered is null.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException(
                        "The array entered contains a null element.");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

