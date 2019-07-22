package graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/flower-planting-with-no-adjacent
 */
public class GraphColoring1 {

    public static void main(String[] args) {
        int[] res = gardenNoAdj(3, new int[][]{{1, 2}, {2, 3}, {3, 1}});
        System.out.println(Arrays.toString(res));
    }

    /**
     * @param N     number of gardens
     * @param paths actually - edges
     * @return gardens[i] is the type of flower planted in the (i+1)-th garden.
     */
    static int[] gardenNoAdj(int N, int[][] paths) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        //Create an empty HashSet for all gardens
        for (int vertex = 1; vertex <= N; vertex++) {
            graph.put(vertex, new HashSet<>());
        }

        //Fill the graph
        for (int[] edge : paths) {
            int vertex = edge[0];
            int adj = edge[1];

            graph.get(vertex).add(adj);
            graph.get(adj).add(vertex);
        }

        int[] gardens = new int[N];

        //for all vertices
        for (int vertex = 1; vertex <= N; vertex++) {

            //Let's figure out all already used colors by all adjacent gardens to this node
            Set<Integer> usedColors = new HashSet<>();
            //for all adjacent gardens
            for (int adj : graph.get(vertex)) {
                int adjColor = gardens[adj - 1];

                //skip all not painted gardens
                if (adjColor == 0) {
                    continue;
                }

                usedColors.add(adjColor);
            }

            for (int color = 1; color < 4; color++) {
                if (!usedColors.contains(color)) {
                    gardens[vertex - 1] = color;
                    break;
                }
            }
        }

        return gardens;
    }
}
