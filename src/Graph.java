import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int vertices;
    private List<Integer>[] adjacencyList;

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    
    public void addEdge(int u, int v) {
        if (u < vertices && v < vertices) {
            adjacencyList[u].add(v);
            adjacencyList[v].add(u);
        }
    }

    // BFS
    public void bfs(int start) {
        if (start >= vertices || start < 0) {
            System.out.println("Invalid starting vertex");
            return;
        }

        boolean[] visited = new boolean[vertices];
        LinkedList<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.print("BFS Traversal: ");
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (Integer neighbor : adjacencyList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // DFS 
    public void dfs(int start) {
        if (start >= vertices || start < 0) {
            System.out.println("Invalid starting vertex");
            return;
        }

        boolean[] visited = new boolean[vertices];
        System.out.print("DFS Traversal: ");
        dfsRec(start, visited);
        System.out.println();
    }

    private void dfsRec(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (Integer neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                dfsRec(neighbor, visited);
            }
        }
    }
    public void display() {
        System.out.println("Graph Adjacency List:");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " → ");
            for (Integer neighbor : adjacencyList[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
    
}
