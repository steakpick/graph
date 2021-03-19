package com.project.graph.model;

import java.util.ArrayList;
import java.util.List;

public class GraphForList {
    List<VertexForList> vertex = new ArrayList<>();

    public GraphForList() {
    }

    public GraphForList(List<VertexForList> vertex) {
        this.vertex = vertex;
    }

    public List<VertexForList> getVertex() {
        return vertex;
    }

    public void setVertex(List<VertexForList> vertex) {
        this.vertex = vertex;
    }
}
