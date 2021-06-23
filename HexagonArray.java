import java.io.*;
import java.util.*;

public class HexagonArray {

    // The array with hexagon costs, starting from 1, 0 ignored for all arrays
    int[] hexagonCosts = new int[234];

    // Array of neighbors for all hexagons. 0th is up, 1 is next clockwise
    int[][] neighbors = new int[234][6];

    // Path cost to the hexagon
    int[] costs = new int[234];

    // Predecessor of the hexagon on path
    int[] predecessors = new int[234];

    // flag to show if the hexagon is already visited
    boolean[] visited = new boolean[234];

    // Array to extract the path to it
    int[] path = new int[234];

    /**
     * 
     * @return int Check which hexagon has the lowest cost at the moment and not yet
     *         visited
     */
    private int getNextHexagon() {
        int m = Integer.MAX_VALUE;
        int h = 0;

        /**
         * Iterate through the hexagons and find the one which is not yet visited and
         * has minimum cost
         */
        for (int i = 1; i <= 233; i++) {
            if (!visited[i]) {
                if (costs[i] < m) {
                    m = costs[i];
                    h = i;
                }
            }
        }
        return h;
    }

    /**
     * 
     * @param int Checks if the path from the current hexagon to the neighbor lowers
     *            the neighbors path cost
     */
    private void updateNeighborsCosts(int hexagon) {
        for (int i = 0; i < 6; i++) {
            int h = neighbors[hexagon][i];
            if ((h != Integer.MAX_VALUE) && (!visited[h])) {
                if (costs[h] > (hexagonCosts[h] + costs[h])) {
                    costs[h] = hexagonCosts[h] + costs[hexagon];
                    predecessors[h] = hexagon;
                }
            }
        }
    }

    public static void main(String[] args) {
        HexagonArray hexArray = new HexagonArray();

        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            while (scanner.hasNextInt()) {
                hexArray.hexagonCosts[scanner.nextInt()] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the neighbors to all hexagons
        for (int i = 1; i <= 233; i++) {
            if (i > 15)
                hexArray.neighbors[i][0] = i - 15;
            else
                hexArray.neighbors[i][0] = Integer.MAX_VALUE;

            if ((i < 9) || ((i % 15) == 8))
                hexArray.neighbors[i][1] = Integer.MAX_VALUE;
            else
                hexArray.neighbors[i][1] = i - 7;

            if ((i > 255) || ((i % 15) == 8))
                hexArray.neighbors[i][2] = Integer.MAX_VALUE;
            else
                hexArray.neighbors[i][2] = i + 8;

            if (i < 219)
                hexArray.neighbors[i][3] = i + 15;
            else
                hexArray.neighbors[i][3] = Integer.MAX_VALUE;

            if ((i > 225) || ((i % 15) == 1))
                hexArray.neighbors[i][4] = Integer.MAX_VALUE;
            else
                hexArray.neighbors[i][4] = i + 7;

            if ((i < 9) || ((i % 15) == 1))
                hexArray.neighbors[i][5] = Integer.MAX_VALUE;
            else
                hexArray.neighbors[i][5] = i - 8;

            // Set the costs to infinity
            hexArray.costs[i] = Integer.MAX_VALUE;
            hexArray.predecessors[i] = -1;
            hexArray.visited[i] = false;
            hexArray.path[i] = 0;

        }

        hexArray.costs[226] = hexArray.hexagonCosts[226];
        int hexagon = 226;

        while (hexagon != 8) {
            hexArray.updateNeighborsCosts(hexagon);
            hexArray.visited[hexagon] = true;
            hexagon = hexArray.getNextHexagon();
        }

        int i = 0;
        while (hexagon != 226) {
            hexArray.path[i] = hexagon;
            hexagon = hexArray.predecessors[hexagon];
            i++;
        }
        hexArray.path[i] = 226;

        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while (i >= 0) {
                System.out.println(hexArray.path[i]);
                bufferedWriter.write(Integer.toString(hexArray.path[i]) + "\n");
                i--;
            }
            System.out.print("MINIMAL COST PATH COSTS: ");
            System.out.println(hexArray.costs[8]);
            bufferedWriter.write("MINIMAL COST PATH COSTS: " + Integer.toString(hexArray.costs[8]) + "\n");
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}