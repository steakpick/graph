package com.project.graph.model;

public class Edge{
    private String name;
    private String firstVertex;
    private String secondVertex;
    private String directedVertex = "";
    private boolean directed;

    public Edge() {
    }

    public Edge(String name, String firstVertex, String secondVertex, String directedVertex, boolean directed) {
        this.name = name;
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.directedVertex = directedVertex;
        this.directed = directed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(String firstVertex) {
        this.firstVertex = firstVertex;
    }

    public String getSecondVertex() {
        return secondVertex;
    }

    public void setSecondVertex(String secondVertex) {
        this.secondVertex = secondVertex;
    }

    public String getDirectedVertex() {
        return directedVertex;
    }

    public void setDirectedVertex(String directedVertex) {
        this.directedVertex = directedVertex;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isRevertEdge(Edge edge){
        return this.firstVertex.equals(edge.getSecondVertex()) && this.secondVertex.equals(edge.getFirstVertex());
    }
}
