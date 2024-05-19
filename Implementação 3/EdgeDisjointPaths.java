import java.io.*;
import java.util.*;

public class EdgeDisjointPaths {
    static final int INF = Integer.MAX_VALUE;
    
    // BFS to find a path from source to sink
    boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[residualGraph.length];
        Arrays.fill(visited, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < residualGraph.length; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }

    // Edmonds-Karp implementation of Ford-Fulkerson algorithm
    int fordFulkerson(int[][] graph, int source, int sink) {
        int u, v;
        int[][] residualGraph = new int[graph.length][graph.length];

        for (u = 0; u < graph.length; u++)
            for (v = 0; v < graph[u].length; v++)
                residualGraph[u][v] = graph[u][v];

        int[] parent = new int[graph.length];
        int maxFlow = 0;

        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = INF;

            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // Function to get the edge disjoint paths
    void getEdgeDisjointPaths(int[][] graph, int source, int sink) {
        int maxFlow = fordFulkerson(graph, source, sink);
        System.out.println("Number of edge disjoint paths: " + maxFlow);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && fordFulkerson(graph, i, j) > 0) {
                    System.out.println("Path: " + i + " -> " + j);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        EdgeDisjointPaths edp = new EdgeDisjointPaths();

        BufferedReader br = new BufferedReader(new FileReader("graph.txt"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        int[][] graph = new int[vertices][vertices];

        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());
            graph[u][v] = capacity;
        }

        st = new StringTokenizer(br.readLine());
        int source = Integer.parseInt(st.nextToken());
        int sink = Integer.parseInt(st.nextToken());

        edp.getEdgeDisjointPaths(graph, source, sink);
    }
}
