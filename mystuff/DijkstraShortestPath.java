import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraShortestPath {

    private Map<String, Map<String, Integer>> graph = new HashMap<>();

    public static void main(String[] args) {

        DijkstraShortestPath dijkstra = new DijkstraShortestPath();

        // Load data from file
        String filename = "C:\\Users\\zuhay\\OneDrive\\My Documents\\Git\\Github\\Graphs_CSC\\src\\data.txt";
        try {
            dijkstra.loadData(filename);
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
            return;
        }

        // Run Dijkstra's algorithm starting at the first node in the file
        String startNode = dijkstra.graph.keySet().stream().findFirst().orElse(null);
        if (startNode == null) {
            System.err.println("Error: no nodes found in the graph");
            return;
        }

        Map<String, Integer> shortestDistances = dijkstra.getShortestDistances(startNode);
        System.out.println("Shortest distances from node " + startNode + ":");
        for (Map.Entry<String, Integer> entry : shortestDistances.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void loadData(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 3) {
                    System.err.println("Invalid input line: " + line);
                    continue;
                }
                String source = parts[0];
                String dest = parts[1];
                int weight = Integer.parseInt(parts[2]);
                addEdge(source, dest, weight);
            }
        }
    }

    public void addEdge(String source, String dest, int weight) {
        Map<String, Integer> neighbors = graph.computeIfAbsent(source, k -> new HashMap<>());
        neighbors.put(dest, weight);
    }

    public Map<String, Integer> getShortestDistances(String startNode) {
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        // Initialize distances to infinity for all nodes except the start node
        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);

        // Add the start node to the queue
        queue.offer(new Node(startNode, 0));

        // Run Dijkstra's algorithm
        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String curr = currNode.name;
            if (visited.contains(curr)) {
                continue;
            }
            visited.add(curr);

            Map<String, Integer> neighbors = graph.get(curr);
            if (neighbors == null) {
                continue;
            }
            for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                String neighbor = entry.getKey();
                int weight = entry.getValue();
                int distanceThroughCurr = distances.get(curr) + weight;
                if (distanceThroughCurr < distances.get(neighbor)) {
                    distances.put(neighbor, distanceThroughCurr);
                    queue.offer(new Node(neighbor, distanceThroughCurr));
                }
            }
        }

        return distances;
    }

    private static class Node implements Comparable
