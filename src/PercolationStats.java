import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats {

    private final int num;
    private final int times;
    private double[] thresholds;
    private final double co = 1.96;

    public PercolationStats(int n, int t) {
        num = n;
        times = t;
        thresholds = new double[times];
    }

    private void simulate() {
        for (int i = 0; i < times; i ++){
            thresholds[i] = getThreshold();

        }

    }
    private double getThreshold() {
        int[] sites = new int[num * num];
        for (int i = 0; i < num * num; i ++) {
            sites[i] = i + 1;
        }
        StdRandom.shuffle(sites);
        Percolation percolation = new Percolation(num);

        int k = 0;
        for (int site: sites) {
            int col = (site - 1) / num + 1;
            int row = (site - 1) % num + 1;
            percolation.open(col, row);
            k ++;
            if (percolation.percolates()) break;
        }

        return (double) k / (double) (num * num);
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {

        return mean() - co * Math.sqrt(stddev() / times);
    }
    public double confidenceHi() {

        return mean() + co * Math.sqrt(stddev() / times);
    }
    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException();
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        if (n <= 0 || t <= 0) throw new IllegalArgumentException();

        PercolationStats ps = new PercolationStats(n, t);
        ps.simulate();
        System.out.println(String.format("mean\t\t\t\t\t\t\t= %s", ps.mean()));
        System.out.println(String.format("stddev\t\t\t\t\t\t\t= %s", ps.stddev()));
        System.out.println(String.format("95%% confidence interval\t\t\t= [%s, %2$s]", ps.confidenceLo(), ps.confidenceHi()));


    }
}
