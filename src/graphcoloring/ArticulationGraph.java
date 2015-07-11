/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphcoloring;

import java.util.Arrays;

/**
 *
 * @author interoperabilidad
 */
public class ArticulationGraph {
    private static class Graph
    {
        int adj[][];
        public Graph(int n)
        {
            adj = new int[n][n];
        }
        public void add(int i, int j)
        {
            adj[i][j] = 1;
        }
    }
    public static void main(String args[]) {
        int g[][] = new int[][]{
            {0, 1, 0, 0},
            {1, 0, 1, 0},
            {0, 1, 0, 1},
            {0, 0, 1, 0},};
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
        System.out.println(Arrays.toString(ap));
        
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
}
