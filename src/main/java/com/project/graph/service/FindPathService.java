package com.project.graph.service;

import com.project.graph.model.Graph;
import com.project.graph.model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FindPathService {
    static final int INF = Integer.MAX_VALUE / 2;

    public static class Edge {
        int v, cost;

        public Edge(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
    }

    private boolean bellmanFord(List<Edge>[] graph, int s, int[] dist, int[] pred) {
        Arrays.fill(pred, -1);
        Arrays.fill(dist, INF);
        dist[s] = 0;
        int n = graph.length;
        boolean updated = false;
        for (int step = 0; step < n; step++) {
            updated = false;
            for (int u = 0; u < n; u++) {
                if (dist[u] == INF) continue;
                for (Edge e : graph[u]) {
                    if (dist[e.v] > dist[u] + e.cost) {
                        dist[e.v] = dist[u] + e.cost;
                        dist[e.v] = Math.max(dist[e.v], -INF);
                        pred[e.v] = u;
                        updated = true;
                    }
                }
            }
            if (!updated)
                break;
        }
        // if updated is true then a negative cycle exists
        return updated == false;
    }

    private int[] findNegativeCycle(List<Edge>[] graph) {
        int n = graph.length;
        int[] pred = new int[n];
        Arrays.fill(pred, -1);
        int[] dist = new int[n];
        int last = -1;
        for (int step = 0; step < n; step++) {
            last = -1;
            for (int u = 0; u < n; u++) {
                if (dist[u] == INF) continue;
                for (Edge e : graph[u]) {
                    if (dist[e.v] > dist[u] + e.cost) {
                        dist[e.v] = Math.max(dist[u] + e.cost, -INF);
                        dist[e.v] = Math.max(dist[e.v], -INF);
                        pred[e.v] = u;
                        last = e.v;
                    }
                }
            }
            if (last == -1)
                return null;
        }
        for (int i = 0; i < n; i++) {
            last = pred[last];
        }
        int[] p = new int[n];
        int cnt = 0;
        for (int u = last; u != last || cnt == 0; u = pred[u]) {
            p[cnt++] = u;
        }
        int[] cycle = new int[cnt];
        for (int i = 0; i < cycle.length; i++) {
            cycle[i] = p[--cnt];
        }
        return cycle;
    }

    private List<Edge>[] modifyGraph(Graph fullGraph) {
        int countOfVertex = fullGraph.getVertexList().size();
        List<Edge>[] graph = Stream.generate(ArrayList::new).limit(4).toArray(List[]::new);

        for (com.project.graph.model.Edge edge : getFilteringEdges(fullGraph)){
            graph[Integer.valueOf(edge.getFirstVertex())].add(new Edge(Integer.valueOf(edge.getSecondVertex()), Integer.valueOf(edge.getName())));
        }

        return graph;
    }

    // Usage example
    public String findPath(Graph fullGraph) {
        List<Edge>[] graph = modifyGraph(fullGraph);
        int[] cycle = findNegativeCycle(graph);
        String output = Arrays.toString(cycle);
        if(output == null) return "Not possible";
        else return output;
    }

    private List<com.project.graph.model.Edge> getFilteringEdges(Graph graph) {
        List<com.project.graph.model.Edge> edgeList = new ArrayList<>();

        for (Vertex vertex : graph.getVertexList()) {
            for (com.project.graph.model.Edge edge : vertex.getEdges()) {
                if (!containsEdge(edgeList, edge)) edgeList.add(edge);
            }
        }

        return edgeList;
    }

    private boolean containsEdge(List<com.project.graph.model.Edge> edgeList, com.project.graph.model.Edge edge) {
        for (com.project.graph.model.Edge e : edgeList) {
            if (e.isRevertEdge(edge)) return true;
        }
        return false;
    }
}
