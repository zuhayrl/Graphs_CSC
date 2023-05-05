import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graphs {

    // Represents an edge in the graph
    private static class Edge {
        int dest;
        int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    // Represents a node in the priority queue used in Dijkstra's algorithm
    private static class Node implements Comparable<Node> {
        int id;
        int dist;

        public Node(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(dist, other.dist);
        }
    }

    public static void main(String[] args) {

        String filename = "C:\\Users\\zuhay\\OneDrive\\University\\Third Year\\3rdYearFirstSem\\CSC2001F\\Assignments\\Assignment 5\\Graphs_CSC\\src";
        List<List<Edge>> graph = new ArrayList<>();

        // Read the data from the file and create the graph
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Determine the number of nodes in the graph
            int numNodes = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int source = parts[0].charAt(0) - 'A';
                int dest = parts[1].charAt(0) - 'A';
                int weight = Integer.parseInt(parts[2]);

                numNodes = Math.max(numNodes, Math.max(source, dest) + 1);
            }

            // Initialize the graph with empty adjacency lists
            for (int i = 0; i < numNodes; i++) {
                graph.add(new ArrayList<>());
            }

            // Read the data again and add the edges to the graph
            BufferedReader bra = new BufferedReader(new FileReader(filename));
            while ((line = bra.readLine()) != null) {
                String[] parts = line.split(" ");
                int source = parts[0].charAt(0) - 'A';
                int dest = parts[1].charAt(0) - 'A';
                int weight = Integer.parseInt(parts[2]);

                graph.get(source).add(new Edge(dest, weight));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run Dijkstra's algorithm from a specified source node
        int sourceNode = 0; // Change this value to specify a different source node
        int[] dist = new int[graph.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[sourceNode] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(sourceNode, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.dist > dist[curr.id]) {
                continue; // Ignore outdated entries in the priority queue
            }

            for (Edge neighbor : graph.get(curr.id)) {
                int newDist = curr.dist + neighbor.weight;

                if (newDist < dist[neighbor.dest]) {
                    dist[neighbor.dest] = newDist;
                    pq.offer(new Node(neighbor.dest, newDist));
                }
            }
        }

        // Print the shortest distances from the source node to all other nodes in the graph
        for (int i = 0; i < dist.length; i++) {
            System.out.printf("%c -> %c: %d%n", sourceNode + 'A', i + 'A', dist[i]);}

    }
}

