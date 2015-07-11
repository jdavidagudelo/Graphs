/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphcoloring;

import static graphcoloring.ArticulationGraph.getArticulations;
import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author interoperabilidad
 */
public class GraphColoring {

    private static class Graph {

        int adj[][];

        public Graph(int n) {
            adj = new int[n][n];
        }

        public void add(int i, int j) {
            adj[i][j] = 1;
        }
    }

    class Edge {

        int u;
        int v;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int graph[][] = new int[][]{
            {0, 1, 1, 0, 1, 0, 0},
            {1, 0, 0, 1, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0}
        };
        int[] color = new int[graph.length];
        System.out.println(coloring(graph, color, 0, 4, 3));
        System.out.println(isBipartite(graph, graph.length));
        Stack<Integer> stack = new Stack<>();
        visited = new boolean[graph.length];
        Arrays.fill(visited, false);
        topologicalSort(0, graph, stack);
        System.out.println(stack);
        topologicalSort(0, graph);
        Arrays.fill(visited, false);
        dfs(graph, 0);
        System.out.println();
        dfs1(graph, 0);

        System.out.println();
        Arrays.fill(visited, false);
        labels = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            labels[i] = Arrays.copyOf(graph[i], graph[i].length);
        }
        System.out.println(getPathDfs1(graph, 0, 5));
        System.out.println(hasCycle(graph));
        System.out.println();
        System.out.println("Connected " + stronglyConnected(
                new int[][]{
                    {0, 1, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 1},
                    {1, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},}
        ));
        System.out.println("Connected " + stronglyConnected(
                new int[][]{
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1},}
        ));
        Graph g = new Graph(2);
        g.add(0, 1);
        System.out.println();
        System.out.println(isBiconnected(g.adj));
        g = new Graph(5);
        g.add(1, 0);
        g.add(0, 2);
        g.add(2, 1);
        g.add(0, 3);
        g.add(3, 4);
        boolean visited[] = new boolean[g.adj.length];
        int[] disc = new int[g.adj.length];
        int[] parent = new int[g.adj.length];
        Arrays.fill(parent, -1);
        int[] low = new int[g.adj.length];
        Arrays.fill(disc, Integer.MAX_VALUE);
        Arrays.fill(low, Integer.MAX_VALUE);
        System.out.println("Bridges");
        for (int i = 0; i < g.adj.length; i++) {
            if (!visited[i]) {
                getBridges(i, g.adj, visited, parent, low, disc);
            }
        }
        System.out.println(isBiconnected(g.adj));
        g = new Graph(3);
        g.add(0, 1);
        g.add(1, 2);
        System.out.println(isBiconnected(g.adj));
        g = new Graph(4);
        GraphColoring.visited = new boolean[g.adj.length];
        g.add(0, 1);
        g.add(0, 3);
        g.add(0, 2);
        g.add(2, 3);
        g.add(1, 3);
        System.out.println("Ways = " + countWalksNaive(g.adj, 0, 3, 2));
        System.out.println("Ways = " + countWalks(g.adj, 0, 3, 2));
    }
    private static boolean visited[];

    public static void topologicalSort(int x, int g[][]) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> queue = new Stack<>();
        queue.push(x);
        while (!queue.isEmpty()) {
            int v = queue.pop();
            if (!visited[v]) {
                continue;
            }
            visited[v] = true;
            for (int i = 0; i < g.length; i++) {
                if (g[i][v] != 0) {
                    queue.push(i);
                }
            }
            stack.push(x);
        }
        System.out.println(stack);
    }
    private static Stack<Integer> path = new Stack<>();
    private static int[][] labels;

    /**
     * Return the path from x to y in a depth first search in the graph g
     * represented as adjacency matrix.
     *
     * @param g
     * @param x
     * @param y
     * @return
     */
    public static String getPathDfs1(int g[][], int x, int y) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<>();
        int prev[] = new int[g.length];
        prev[x] = -1;
        stack.push(x);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            System.out.print(v + " ");
            if (v == y) {

                StringBuilder path = new StringBuilder(y + "");
                int i = y;
                while (prev[i] != -1) {
                    path.insert(0, prev[i] + ", ");
                    i = prev[i];
                }
                return path.toString();
            }
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] == 1) {
                    if (!visited[i]) {
                        prev[i] = v;
                        stack.push(i);
                    }
                }
            }
        }
        return "";
    }

    public static Stack getPathDfs(int g[][], int x, int y) {
        if (visited[x]) {
            Stack<Integer> r = new Stack<>();
            r.addAll(path);
            return r;
        }
        System.out.print(x + " ");
        visited[x] = true;
        if (x == y) {
            Stack<Integer> r = new Stack<>();
            r.addAll(path);
            System.out.println(" WTF " + x + " " + path);
            return r;
        }
        for (int i = 0; i < g.length; i++) {
            if (labels[x][i] == 1) {
                if (!visited[i]) {
                    path.push(x);
                    path.push(i);
                    labels[x][i] = 2;
                    getPathDfs(g, i, y);
                    path.pop();
                    path.pop();
                } else {
                    labels[x][i] = 3;
                }
            }
        }
        Stack<Integer> r = new Stack<>();
        r.addAll(path);
        return r;
    }

    public static void union(int parent[], int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    public static int find(int parent[], int x) {
        if (parent[x] == -1) {
            return x;
        }
        return find(parent, parent[x]);
    }

    /**
     * Returns true if it is possible to visit all the vertices in the graph
     * starting from vertex x, false otherwise.
     *
     * @param g
     * @param x
     * @return
     */
    public static boolean testConnectivity(int[][] g, int x) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<>();
        stack.push(x);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            System.out.print(v + " ");
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] == 1 && !visited[i]) {
                    stack.push(i);
                }
            }
        }
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param g
     * @return
     */
    public static boolean stronglyConnected(int[][] g) {
        if (!testConnectivity(g, 0)) {
            return false;
        }
        int[][] gt = new int[g.length][g.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                gt[i][j] = g[j][i];
            }
        }
        return testConnectivity(gt, 0);
    }

    /**
     * Test cycle in graph using union find test.
     *
     * @param g
     * @return
     */
    public static boolean hasCycle(int g[][]) {
        int[] parent = new int[g.length];
        Arrays.fill(parent, -1);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (g[i][j] > 0) {
                    int xset = find(parent, i);
                    int yset = find(parent, j);
                    if (xset == yset) {
                        return true;
                    }
                    union(parent, i, j);
                }
            }
        }
        return false;
    }

    //dfs iterative
    public static void dfs1(int g[][], int x) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> temp = new Stack<>();
        stack.push(x);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            System.out.print(v + " ");
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] == 1 && !visited[i]) {
                    stack.push(i);
                }
            }
        }
    }

    public static void dfs(int g[][], int x) {
        if (visited[x]) {
            return;
        }
        System.out.print(x + " ");
        visited[x] = true;
        for (int i = 0; i < g.length; i++) {
            if (g[x][i] != 0) {
                dfs(g, i);
            }
        }
    }

    public static void topologicalSort(int x, int g[][], Stack<Integer> stack) {
        if (visited[x]) {
            return;
        }
        visited[x] = true;
        for (int i = 0; i < g.length; i++) {
            if (g[x][i] != 0) {
                topologicalSort(i, g, stack);
            }
        }
        stack.push(x);
    }

    public static boolean isBipartite(int g[][], int N) {
        int[] color = new int[N];
        return coloring(g, color, 0, N, 2);
    }

    public static boolean safeColor(int g[][], int color[], int c) {
        for (int i = 0; i < g.length; i++) {
            if (g[i][c] != 0) {
                if (color[i] == c) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests if the input graph can be colored with at most C colors.
     *
     * @param g the adjacency matrix of the graph.
     * @param color current colors selected for the vertices from 0 to v-1
     * @param v the current vertex to be colored.
     * @param V the number of vertices of the graph.
     * @param C the total number of color available.
     * @return true if the graph can be colored with C different colors, false
     * otherwise.
     */
    public static boolean coloring(int g[][], int color[], int v, int V, int C) {
        if (v == V) {
            return true;
        }
        for (int c = 1; c <= C; c++) {
            if (safeColor(g, color, c)) {
                color[v] = c;
                if (coloring(g, color, v + 1, V, C)) {
                    return true;
                }
                color[v] = 0;
            }
        }
        return false;
    }

    public static boolean isBiconnected(int[][] g) {
        //test the graph is connected
        if (!stronglyConnected(g)) {
            return false;
        }
        boolean visited[] = new boolean[g.length];
        int[] disc = new int[g.length];
        int[] parent = new int[g.length];
        Arrays.fill(parent, -1);
        int[] low = new int[g.length];
        Arrays.fill(disc, Integer.MAX_VALUE);
        Arrays.fill(low, Integer.MAX_VALUE);
        boolean ap[] = new boolean[g.length];
        for (int i = 0; i < g.length; i++) {
            if (!visited[i]) {
                getArticulations(i, g, visited, parent, low, disc, ap);
            }
        }
        //test the graph has not articulations
        for (boolean a : ap) {
            if (a) {
                return false;
            }
        }
        return true;
    }
    private static int time = 0;

    public static void getArticulations(int u, int[][] g, boolean visited[], int[] parent, int low[], int disc[], boolean ap[]) {
        visited[u] = true;
        int children = 0;
        low[u] = disc[u] = ++time;
        //0(n)
        for (int i = 0; i < g.length; i++) {
            if (g[u][i] > 0) {
                if (!visited[i]) {
                    children++;
                    parent[i] = u;
                    getArticulations(i, g, visited, parent, low, disc, ap);
                    low[u] = Math.min(low[u], low[i]);
                    //root with 2 or more children, is a articulation
                    if (parent[u] == -1 && children > 1) {
                        ap[u] = true;
                    }
                    //articulation not root
                    if (parent[u] != -1 && low[i] >= disc[u]) {
                        ap[u] = true;
                    }
                } else if (i != parent[u]) {
                    low[u] = Math.min(low[u], disc[i]);
                }
            }
        }
    }

    public static void getBridges(int u, int[][] g, boolean visited[], int[] parent, int low[], int disc[]) {
        visited[u] = true;
        low[u] = disc[u] = ++time;
        //0(n)
        for (int i = 0; i < g.length; i++) {
            if (g[u][i] > 0) {
                if (!visited[i]) {
                    parent[i] = u;
                    getBridges(i, g, visited, parent, low, disc);
                    low[u] = Math.min(low[u], low[i]);
                    //root with 2 or more children, is a articulation
                    if (low[i] > disc[u]) {
                        System.out.println(u + ", " + i);
                    }
                    //articulation not root
                } else if (i != parent[u]) {
                    low[u] = Math.min(low[u], disc[i]);
                }
            }
        }
    }

    public static int degree(int g[][], int x) {
        int r = 0;
        for (int i = 0; i < g.length; i++) {
            r += g[x][i] > 0 ? 1 : 0;
        }
        return r;
    }

    private enum Euler {

        CYCLE, PATH, NONE
    };

    public static Euler getEuler(int g[][]) {
        if (!isConnected(g)) {
            return Euler.NONE;
        }
        int odd = 0;
        for (int i = 0; i < g.length; i++) {
            if (degree(g, i) % 2 != 0) {
                odd++;
            }
        }
        if (odd > 2) {
            return Euler.NONE;
        }
        return odd == 1 ? Euler.PATH : Euler.CYCLE;
    }

    public static boolean isConnected(int[][] g) {
        boolean visited[] = new boolean[g.length];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            for (int i = g.length - 1; i >= 0; i--) {
                if (g[v][i] != 0) {
                    stack.push(i);
                }
            }
        }
        for (int i = 0; i < g.length; i++) {
            if (!visited[i] && degree(g, i) > 0) {
                return false;
            }
        }
        return true;
    }
    public int[][] closure(int[][] g)
    {
        int[][] x = new int[g.length][g.length];
        for(int i = 0; i < g.length; i++)
        {
            x[i] = Arrays.copyOf(g[i],g[i].length);
        }
        for(int k = 0; k < g.length; k++)
        {
            for(int i = 0; i < g.length; i++)
            {
                for(int j = 0; j < g.length; j++)
                {
                    if(x[i][j] == 0)
                    {
                        x[i][j] = x[i][k] == 1 && x[k][j] == 1? 1 : 0;
                    }
                }
            }
        }
        return x;
    }
    public static int countWalks(int[][] g, int x, int y, int k) {
        int dp[][][] = new int[g.length][g.length][k + 1];

        for (int e = 0; e <= k; e++) {
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
                    {
                        dp[i][j][e] = 0;
                        if (e == 0 && i == j) {
                            dp[i][j][e] = 1;
                        }
                        if (e == 1 && g[i][j] > 0) {
                            dp[i][j][e] = 1;
                        }
                        if (e > 1) {
                            for (int v = 0; v < g.length; v++) {
                                if (g[i][v] > 0) {
                                    dp[i][j][e] += dp[v][j][e - 1];
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[x][y][k];
    }

    /**
     * O(v pow k)
     *
     * @param g
     * @param x
     * @param y
     * @param k
     * @return
     */
    public static int countWalksNaive(int[][] g, int x, int y, int k) {
        visited[x] = true;
        if (x == y && k == 0) {
            return 1;
        }
        if (k <= 0) {
            return 0;
        }
        int r = 0;
        for (int i = 0; i < g.length; i++) {
            if (g[x][i] > 0) {
                r += countWalksNaive(g, i, y, k - 1);
            }
        }
        return r;
    }

}
