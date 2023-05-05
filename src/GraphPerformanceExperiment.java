import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GraphPerformanceExperiment {
    private static final String GRAPH_FILENAME_FORMAT = "Graph_%d_%d.txt";
    private static final Random RANDOM = new Random();
    
    private static class Edge {
        public final int from;
        public final int to;
        public final int cost;
        
        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
    
    private static class Graph {
        public final int V;
        public final List<Edge>[] adj;
        
        @SuppressWarnings("unchecked")
        public Graph(int V) {
            this.V = V;
            this.adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<Edge>();
            }
        }
        
        public void addEdge(int from, int to, int cost) {
            adj[from].add(new Edge(from, to, cost));
        }
        
        public List<Edge> getEdges(int v) {
            return adj[v];
        }
    }
    
    private static class Dijkstra {
        private final Graph graph;
        private final int source;
        private final int[] distTo;
        private final int[] edgeTo;
        private int vertexOpsCount;
        private int edgeOpsCount;
        
        public Dijkstra(Graph graph, int source) {
            this.graph = graph;
            this.source = source;
            this.distTo = new int[graph.V];
            this.edgeTo = new int[graph.V];
            Arrays.fill(distTo, Integer.MAX_VALUE);
            Arrays.fill(edgeTo, -1);
            distTo[source] = 0;
            edgeOpsCount = 0;
            vertexOpsCount = 0;
            
            run();
        }
        
        private void run() {
            MinPQ<Integer> pq = new MinPQ<>(graph.V);
            pq.insert(source, 0);
            vertexOpsCount++;
            while (!pq.isEmpty()) {
                int v = pq.delMin();
                vertexOpsCount++;
                for (Edge e : graph.getEdges(v)) {
                    edgeOpsCount++;
                    relax(e);
                }
            }
        }
        
        private void relax(Edge e) {
            int v = e.from;
            int w = e.to;
            if (distTo[w] > distTo[v] + e.cost) {
                distTo[w] = distTo[v] + e.cost;
                edgeTo[w] = v;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                    vertexOpsCount++;
                }
            }
        }
        
        public int getDistTo(int v) {
            return distTo[v];
        }
        
        public int getVertexOpsCount() {
            return vertexOpsCount;
        }
        
        public int getEdgeOpsCount() {
            return edgeOpsCount;
        }
    }
    
    private static void saveGraphToFile(Graph graph, String filename) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (int v = 0; v < graph.V; v++) {
                for (Edge e : graph.getEdges(v)) {
                    out.printf("Node%d Node%d %d\n", e.from, e.to, e
