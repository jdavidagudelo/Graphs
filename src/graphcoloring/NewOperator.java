package graphcoloring;

import java.util.*;

public class NewOperator {
	//given the constraints this is the maximum possible value
    //that could be generated.

    private final int max = 5 * 9 * 9 * 9 + 9 * 9 * 6 + 9;
	//Arrays used to save time during the 
    //bfs, it is very important to do it.
    private final int sum[] = new int[max + 1];
    private int first[] = new int[max + 1];
    private int smallest[] = new int[max + 1];
    private int prod3[] = new int[max + 1];

    class Node implements Comparable<Node> {

        int x;
        int cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }

        public int compareTo(Node node) {
            if (Integer.compare(cost, node.cost) != 0) {
                return Integer.compare(cost, node.cost);
            }
            return Integer.compare(x, node.x);
        }

        public String toString() {
            return "(" + x + ", " + cost + ")";
        }
    }

    public int bfs(int x, int goal) {
        TreeSet<Node> queue = new TreeSet<>();
        queue.add(new Node(x, 0));
        BitSet set = new BitSet();
        //List of all valid nodes, includes the x value and the cost to get there.
        List<Node> valid = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node c = queue.pollFirst();
            //evety possible node is visited
            if (set.cardinality() == max) {
                return -1;
            }
            if (set.get(c.x)) {
                continue;
            }
            valid.add(c);
            if (c.x == goal) {
                return c.cost;
            }
            set.set(c.x);
            for (Node n : valid) {
                //the operation is not conmutative
                int r = arroba(c.x, n.x);
                if (!set.get(r)) {
                    //cost of creating n + cost of creating c + 1
                    queue.add(new Node(r, n.cost + c.cost + 1));
                }
                r = arroba(n.x, c.x);
                if (!set.get(r)) {
                    queue.add(new Node(r, n.cost + c.cost + 1));
                }
            }
        }
        return -1;
    }

    public int sum(int x) {
        if (x <= max && sum[x] != -1) {
            return sum[x];
        }
        String s = "" + x;
        int r = 0;
        for (int i = 0; i < s.length(); i++) {
            r += Integer.parseInt(s.charAt(i) + "");
        }
        return r;
    }

    public int first(int x) {
        if (x <= max && first[x] != -1) {
            return first[x];
        }
        String s = "" + x;
        return Integer.parseInt(s.charAt(0) + "");
    }

    public int smallest(int x) {
        if (x <= max && smallest[x] != -1) {
            return smallest[x];
        }
        String s = "" + x;
        int min = Integer.parseInt(s.charAt(0) + "");
        for (int i = 1; i < s.length(); i++) {
            min = Math.min(min, Integer.parseInt(s.charAt(i) + ""));
        }
        return min;
    }

    public int prod3(int x) {
        if (x <= max && prod3[x] != -1) {
            return prod3[x];
        }
        String s = "" + x;
        if (s.length() < 3) {
            return prod(x);
        }
        int[] a = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            a[i] = Integer.parseInt(s.charAt(i) + "");
        }
        Arrays.sort(a);
        return a[a.length - 1] * a[a.length - 2] * a[a.length - 3];
    }

    public int prod(int x) {
        String s = "" + x;
        int r = 1;
        for (int i = 0; i < s.length(); i++) {
            r *= Integer.parseInt(s.charAt(i) + "");
        }
        return r;
    }

    public int arroba(int x, int y) {
        return 5 * prod3(x) + first(x) * sum(y) + smallest(y);
    }

    public int minimumCount(int x, int goal) {
        if (goal > max) {
            return x == goal ? 0 : -1;
        }

        Arrays.fill(sum, -1);
        Arrays.fill(first, -1);
        Arrays.fill(smallest, -1);
        Arrays.fill(prod3, -1);

        for (int i = 0; i <= max; i++) {
            sum[i] = sum(i);
            first[i] = first(i);
            smallest[i] = smallest(i);
            prod3[i] = prod3(i);
        }
        return bfs(x, goal);
    }
}
