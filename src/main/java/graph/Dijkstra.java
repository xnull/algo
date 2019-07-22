package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * NOT SOLVED, ALMOST SOLVED. ISSUE WITH visited and path
 */
public class Dijkstra {
    public static void main(String[] arr) {
        Graph graph = new Graph();
        graph.add('a', 'b', 1);
        graph.add('b', 'c', 1);
        graph.add('a', 'c', 3);
        graph.add('a', 'd', 10);
        graph.add('c', 'd', 2);

        Collection<Vertex> path = graph.dijkstra('a', 'd');

        System.out.println(path);
    }
}

class Graph {
    Map<Character, SortedSet<Edge>> adjacents = new HashMap<>();

    void add(char vertex, char adjacent, int distance) {
        adjacents.putIfAbsent(vertex, new TreeSet<>());
        adjacents.putIfAbsent(adjacent, new TreeSet<>());

        adjacents
                .get(vertex)
                .add(new Edge(vertex, adjacent, distance));
    }

    Collection<Vertex> dijkstra(char source, char dest) {
        TreeMap<Character, Vertex> path = new TreeMap<>();

        Set<Character> visited = new HashSet<>();

        Queue<Character> queue = new LinkedList<>();
        queue.add(source);

        Vertex initVertex = new Vertex(source, 0);
        path.put(source, initVertex);

        while (!queue.isEmpty()) {
            char currVertex = queue.poll();

            for (Edge currEdge : adjacents.get(currVertex)) {
                if (visited.contains(currEdge.adjacent)) {
                    continue;
                }

                if (path.containsKey(currEdge.adjacent)) {
                    Vertex foundDistance = path.get(currEdge.adjacent);
                    System.out.println("Next distance: " + foundDistance + ", edge: " + currEdge);

                    Vertex minDistance = path.get(currEdge.adjacent);
                    int newDistance = minDistance.distance + currEdge.distance;

                    if (foundDistance.distance > newDistance) {
                        path.remove(currEdge.adjacent);
                        Vertex updatedDistance = new Vertex(currEdge.adjacent, newDistance);
                        System.out.println("Update distance: " + updatedDistance);
                        path.put(currEdge.adjacent, updatedDistance);
                    } else {
                        System.out.println("Ignore next distance: " + foundDistance);
                    }
                } else {
                    //just calc distance - there is no distances to that vertex in the graph yet
                    Vertex updatedDistance = new Vertex(currEdge.adjacent, currEdge.distance);
                    System.out.println("Add distance: " + updatedDistance);
                    path.put(currEdge.adjacent, updatedDistance);
                }

                queue.add(currEdge.adjacent);
                //System.out.println(currEdge.vertex + "->" + currEdge.adjacent + "->" + currEdge.distance + ";");
            }

            visited.add(currVertex);
        }

        return path.values();
    }
}

class Edge implements Comparable<Edge> {
    char vertex;
    char adjacent;
    int distance;

    Edge(char vertex, char adjacent, int distance) {
        this.vertex = vertex;
        this.adjacent = adjacent;
        this.distance = distance;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(distance, other.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, adjacent);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        Edge other = (Edge) o;
        return vertex == other.vertex && adjacent == other.adjacent;
    }

    @Override
    public String toString() {
        return "[" + vertex + "->" + adjacent + "->" + distance + "]";
    }
}

class Vertex implements Comparable<Vertex> {
    char id;
    int distance;

    Vertex(char id, int distance) {
        this.id = id;
        this.distance = distance;
    }

    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(distance, other.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        Vertex other = (Vertex) o;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "[" + id + ":" + distance + "]";
    }
}
