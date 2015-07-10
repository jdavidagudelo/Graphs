/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphcoloring;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author interoperabilidad
 */
public class GraphColoring {

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
        labels = Arrays.copyOf(graph, graph.length);
        System.out.println(getPathDfs(graph, 0, 5));
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
    class Edge
    {
        int v;
        int u;
        public Edge(int u, int v)
        {
            this.u = u; this.v = v;
        }
    }
    public static Stack getPathDfs(int g[][], int x, int y) {
        if (visited[x]) {
            Stack<Integer> r = new Stack<>();
            r.addAll(path);
            return r;
        }
        System.out.print(x+" ");
        visited[x] = true;
        if (x == y) {
            Stack<Integer> r = new Stack<>();
            r.addAll(path);
            System.out.println(" WTF "+x+" "+path);
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
                }
                else
                {
                    labels[x][i] = 3;
                    labels[i][x] = 3;
                }
            }
        }
        Stack<Integer> r = new Stack<>();
        r.addAll(path);
        return r;
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
            for (int i = 0; i < g.length; i++) {
                if (g[v][i] != 0 && !visited[i]) {
                    temp.push(i);
                }
            }
            while (!temp.isEmpty()) {
                stack.push(temp.pop());
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

}
