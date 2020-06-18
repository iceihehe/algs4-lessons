import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;


public class PercolationStats {

    private int N;
    private int T;
    private double[] thresholds;

    public PercolationStats(int n, int t) {
        N = n;
        T = t;
        thresholds = new double[T];
    }

    public void simulate() {
        for (int i = 0; i < T; i ++){
            thresholds[i] = getThreshold();

        }

    }
    public double getThreshold() {
        int[] sites = new int[N * N];
        for (int i = 0; i < N * N; i ++) {
            sites[i] = i + 1;
        }
        StdRandom.shuffle(sites);
        Percolation percolation = new Percolation(N);

        int k = 0;
        for (int site: sites) {
            int col = (site - 1) / N + 1;
            int row = (site - 1) % N + 1;
            percolation.open(col, row);
            k ++;
            if (percolation.percolates()) break;
        }

        return (double) k / (double) (N * N);
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {

        return mean() - 1.96 * Math.sqrt(stddev() / T);
    }
    public double confidenceHi() {

        return mean() + 1.96 * Math.sqrt(stddev() / T);
    }
    public static void main(String[] args) {
//        if (args.length != 2) throw new IllegalArgumentException();
//        int n = Integer.parseInt(args[0]);
//        int t = Integer.parseInt(args[1]);
        int n = 200;
        int t = 100;

        if (n <= 0 || t <= 0) throw new IllegalArgumentException();
        PercolationStats ps = new PercolationStats(n, t);
        ps.simulate();
        System.out.println(String.format("mean\t\t= %s", ps.mean()));
        System.out.println(String.format("stddev\t\t= %s", ps.stddev()));
        System.out.println(String.format("95%% confidence interval\t\t= [%s, %2$s]", ps.confidenceLo(), ps.confidenceHi()));

    }
}
