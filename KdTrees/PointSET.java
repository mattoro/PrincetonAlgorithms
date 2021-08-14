/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p);
        points.add(p);

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        Point2D minPoint = new Point2D(rect.xmin(), rect.ymin());
        Point2D maxPoint = new Point2D(rect.xmax(), rect.ymax());
        List<Point2D> inRect = new LinkedList<>();
        // The sub-set is inclusive
        for (Point2D p : points.subSet(minPoint, true, maxPoint, true)) {
            // The y-coordinate has been validated by the minPoint & maxPoint
            // now check if within x coordinates
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax()) {
                inRect.add(p);
            }
        }
        return inRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (isEmpty()) {
            return null;
        }

        // 1. Find the 2 points next to p in natural order, then find the
        //    closest distance.
        // 2. Widen the searching set to a circle of `d`, save the nearest.
        Point2D next = points.ceiling(p);
        Point2D prev = points.floor(p);
        if (next == null && prev == null) {
            return null;
        }

        double distNext = next == null ? Double.POSITIVE_INFINITY : p.distanceTo(next);
        double distPrev = prev == null ? Double.POSITIVE_INFINITY : p.distanceTo(prev);
        double d = Math.min(distNext, distPrev);

        // create range
        Point2D minPoint = new Point2D(p.x(), p.y() - d);
        Point2D maxPoint = new Point2D(p.x(), p.y() + d);
        Point2D nearest = next == null ? prev : next;  // cannot be both null

        // The sub-set is inclusives
        for (Point2D candidate : points.subSet(minPoint, true, maxPoint, true)) {
            if (p.distanceTo(candidate) < p.distanceTo(nearest)) {
                nearest = candidate;
            }
        }
        return nearest;
    }

    private void checkNull(Object obj) {
        if (obj == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {

    }
}
