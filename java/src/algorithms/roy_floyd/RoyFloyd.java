package algorithms.roy_floyd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

/**
 * @author Sebastian Claici
 */
public class RoyFloyd {

    /**
     * Representation of a Graph data structure.
     */
    public static class Graph {
        private final boolean[][] adjacencyMatrix;
        private final int[][] costMatrix;

        private final int size;

        /**
         * Creates a new Graph object with sizes nodes. Each graph is
         * represented by the adjacency matrix (whether two nodes are
         * connected), and the cost matrix (the cost to travel between
         * two nodes).
         *
         * @param size Number of nodes in the graph.
         */
        public Graph(int size) {
            this.size = size;

            adjacencyMatrix = new boolean[size][size];
            costMatrix = new int[size][size];
        }

        /**
         * Add an edge between two nodes of certain cost.
         *
         * @param src Source node.
         * @param dst Destination node.
         * @param cost Cost of the edge.
         */
        public void addEdge(int src, int dst, int cost) {
            if (cost != 0)
                adjacencyMatrix[src][dst] = true;
            costMatrix[src][dst] = cost;
        }

        /**
         * Computes the all-sources shortest paths of this graph.
         *
         * @return An array representing the cost matrix of the shortest paths.
         */
        public int[][] royFloyd() {
            int[][] result = new int[size][size];
            for (int i = 0; i < size; ++i)
                for (int j = 0; j < size; ++j) {
                    result[i][j] = costMatrix[i][j];
                    if (i != j && result[i][j] == 0)
                        result[i][j] = Integer.MAX_VALUE / 2 - 1;
                }

            //Try to find a better path for each pair (i, j) through an auxiliary node k.
            for (int k = 0; k < size; ++k)
                for (int i = 0; i < size; ++i)
                    for (int j = 0; j < size; ++j) {
                        result[i][j] = Math.min(result[i][j], result[i][k] + result[k][j]);
                    }

            return result;
        }
    }

    // Test code
    public static void main(String[] args) throws Exception {
        BufferedReader buf = new BufferedReader(new FileReader("royfloyd.in"));

        int N = Integer.parseInt(buf.readLine());
        Graph graph = new Graph(N);
        for (int i = 0; i < N; ++i) {
            StringTokenizer st = new StringTokenizer(buf.readLine());
            for (int j = 0; j < N; ++j) {
                int cost = Integer.parseInt(st.nextToken());
                graph.addEdge(i, j, cost);
            }
        }

        int[][] result = graph.royFloyd();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                System.out.printf("%d ", result[i][j]);
            }
            System.out.println();
        }
    }
}