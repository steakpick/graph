package com.project.graph.model;

import java.util.List;

public class VertexForList {
    List<Edge> edgeList;
    private String name;

    public VertexForList() {
    }

    public VertexForList(List<Edge> edgeList, String name) {
        this.edgeList = edgeList;
        this.name = name;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Edge {
        int v, cost = 0;

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Edge(){

        }

        public Edge(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
    }
}
