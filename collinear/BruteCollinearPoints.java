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

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] sortedP = points.clone();
        Arrays.sort(sortedP);
        checkDuplicate(sortedP);

        final int length = sortedP.length;
        List<LineSegment> list = new LinkedList<>(); // to add line segments

        for (int a = 0; a < length; a++) {
            Point pt1 = sortedP[a];

            for (int b = a + 1; b < length; b++) {
                Point pt2 = sortedP[b];
                double slope12 = pt1.slopeTo(pt2);

                for (int c = b + 1; c < length; c++) {
                    Point pt3 = sortedP[c];
                    double slope13 = pt1.slopeTo(pt3);
                    if (slope12 == slope13) {

                        for (int d = c + 1; d < length; d++) {
                            Point pt4 = sortedP[d];
                            double slope14 = pt1.slopeTo(pt4);
                            if (slope12 == slope14) {
                                list.add(new LineSegment(pt1, pt4));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
