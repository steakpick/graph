package com.project.graph.model;

public class Matrix {
    private Integer[][] matrix;
    private Integer countOfVertex;
    private Integer countOfEdge;

    public Matrix() {
    }

    public Matrix(Integer[][] matrix, Integer countOfVertex, Integer countOfEdge) {
        this.matrix = matrix;
        this.countOfVertex = countOfVertex;
        this.countOfEdge = countOfEdge;
    }

    public Integer[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Integer[][] matrix) {
        this.matrix = matrix;
    }

    public Integer getCountOfVertex() {
        return countOfVertex;
    }

    public void setCountOfVertex(Integer countOfVertex) {
        this.countOfVertex = countOfVertex;
    }

    public Integer getCountOfEdge() {
        return countOfEdge;
    }

    public void setCountOfEdge(Integer countOfEdge) {
        this.countOfEdge = countOfEdge;
    }
}
