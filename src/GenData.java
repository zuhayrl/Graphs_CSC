import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GenData {

    public static void makeData(int numVertices, int numEdges){
        Random rand = new Random();
        Set<String> edges = new HashSet<>();
        String[] vertices = new String[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = String.format("Node%03d", i+1);
        }
        
        try {
            FileWriter writer = new FileWriter("data.txt");
            for (int i = 0; i < numEdges; i++) {
                String vertex1 = vertices[rand.nextInt(numVertices)];
                String vertex2 = vertices[rand.nextInt(numVertices)];
                int weight = rand.nextInt(10) + 1;
                
                while (vertex1.equals(vertex2) || edges.contains(vertex1 + " " + vertex2)) {
                    vertex1 = vertices[rand.nextInt(numVertices)];
                    vertex2 = vertices[rand.nextInt(numVertices)];
                }
                
                edges.add(vertex1 + " " + vertex2);
                
                writer.write(vertex1 + " " + vertex2 + " " + weight + "\n");
            }
            writer.close();
            
            System.out.println("Graph generated successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the graph.");
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args){
        makeData(50, 80);
    }
}   