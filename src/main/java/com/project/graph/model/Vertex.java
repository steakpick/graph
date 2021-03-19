package com.project.graph.model;

import java.util.List;

public class Vertex{
    private String name;
    List<Edge> edges;

    public Vertex() {
    }

    public Vertex(String name, List<Edge> edges) {
        this.name = name;
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
