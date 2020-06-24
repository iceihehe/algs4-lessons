import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        System.out.println(k);
        while (!StdIn.isEmpty()){
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        while (k > 0){
            StdOut.println(queue.dequeue());
            k--;
        }

    }
}
