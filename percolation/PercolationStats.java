/* *****************************************************************************
 *  Name:              Matthew Santoro
 *  Coursera User ID:  123456
 *  Last modified:     7/24/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int tCount;
    private final double[] tResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be > 0");
        }
        tCount = trials;
        tResults = new double[tCount];

        for (int t = 0; t < tCount; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }
            int openSites = perc.numberOfOpenSites();
            double result = (double) openSites / (n * n);
            tResults[t] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(tResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(tResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev())) / Math.sqrt(tCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev())) / Math.sqrt(tCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        int deminsions = 10;
        int tCount = 10;
        if (args.length == 2) {
            deminsions = Integer.parseInt(args[0]);
            tCount = Integer.parseInt(args[1]);
        }

        PercolationStats stats = new PercolationStats(deminsions, tCount);
        String confidence = "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]";
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
