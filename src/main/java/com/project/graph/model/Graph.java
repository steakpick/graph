package com.project.graph.model;

import java.util.List;

public class Graph {
    private List<Vertex> vertexList;

    public Graph() {
    }

    public Graph(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    private boolean removeRevertedEdge(Edge edge){
        for (Vertex vertex : vertexList) {
            for (Edge edgeVar : vertex.getEdges()) {
                if (edgeVar.isRevertEdge(edge)) {
                    vertex.getEdges().remove(edgeVar);
                    return true;
                }
            }
        }

        return false;
    }
}
