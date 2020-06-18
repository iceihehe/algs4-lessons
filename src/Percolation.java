import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int N;
    private WeightedQuickUnionUF uf;
    private boolean[] sites;
    private boolean connected = false;
    private int numberOfOpened = 0;

    private int getIndex (int row, int col) {
        return (row - 1) * N + col;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException();

        N = n;
        uf = new WeightedQuickUnionUF(N * N + 2);
        sites = new boolean[N * N + 1];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        int index = getIndex(row, col);
        if (sites[index]) return;
        numberOfOpened ++;
        sites[index] = true;
        // the top
        if (row == 1) {
            uf.union(index, 0);
        }
        if (row == N) {
            uf.union(index, N * N + 1);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(index, getIndex(row - 1, col));

        }
        if (row < N && isOpen(row + 1, col)) {
            uf.union(index, getIndex(row + 1, col));

        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(index, getIndex(row, col - 1));

        }
        if (col < N && isOpen(row, col + 1)) {
            uf.union(index, getIndex(row, col + 1));

        }

        if (uf.find(0) == uf.find(N * N + 1)) {
            connected = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        return sites[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        return uf.find(0) == uf.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numberOfOpened;
    }

    // does the system percolate?
    public boolean percolates(){
        return connected;
    }

    // test client (optional)
    public static void main(String[] args){
//        int n = StdIn.readInt();
        int n = 3;
        Percolation p = new Percolation(n);
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.percolates());
        p.open(2, 1);
        System.out.println(p.percolates());
        p.open(3, 2);
        System.out.println(p.percolates());
        p.open(2, 3);
        System.out.println(p.percolates());
        p.open(2, 2);
        System.out.println(p.percolates());
    }
}