/* *****************************************************************************
 *  Name:              Matthew Santoro
 *  Coursera User ID:  123456
 *  Last modified:     7/22/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TVSITE = 0;
    private int openSites = 0;
    private WeightedQuickUnionUF sites;
    private boolean[][] openess;
    private int deminsions;
    private final int bVSITE;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // n must be greater than 0
        // data type: weighted quick unionUF w/ n*n + 2 sites
        if (n > 0) {
            deminsions = n;
            openess = new boolean[deminsions][deminsions];
            sites = new WeightedQuickUnionUF(n * n + 2);
            bVSITE = deminsions * deminsions + 1;
        }
        else {
            throw new IllegalArgumentException("n must be greater than 0");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        testBoundaries(row, col);
        if (isOpen(row, col)) {
            return;
        }

        // open site and increment open sites
        openess[row - 1][col - 1] = true;
        openSites++;
        int siteIndex = getSiteIndex(row, col);

        // check edge cases first
        if (row == deminsions) sites.union(getSiteIndex(row, col), bVSITE);
        if (row == 1) sites.union(getSiteIndex(row, col), TVSITE);

        // check open left
        if (isInBounds(row, col - 1) && isOpen(row, col - 1)) {
            sites.union(getSiteIndex(row, col - 1), siteIndex);
        }
        // check open right
        if (isInBounds(row, col + 1) && isOpen(row, col + 1)) {
            sites.union(getSiteIndex(row, col + 1), siteIndex);
        }
        // check open top
        if (isInBounds(row - 1, col) && isOpen(row - 1, col)) {
            sites.union(getSiteIndex(row - 1, col), siteIndex);
        }
        // check open bottom
        if (isInBounds(row + 1, col) && isOpen(row + 1, col)) {
            sites.union(getSiteIndex(row + 1, col), siteIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        testBoundaries(row, col);
        return openess[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        testBoundaries(row, col);
        return (sites.find(TVSITE) == sites.find(getSiteIndex(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.find(TVSITE) == sites.find(bVSITE);
    }

    // gets the index of the quick find site given a row and col
    private int getSiteIndex(int row, int col) {
        return deminsions * (row - 1) + col;
    }

    private void testBoundaries(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private boolean isInBounds(int row, int col) {
        return (row >= 1 && col >= 1 && row <= deminsions && col <= deminsions);
    }

    // test client (optional)
    // public static void main(String[] args)

}
