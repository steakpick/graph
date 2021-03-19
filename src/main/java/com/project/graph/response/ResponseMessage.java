package com.project.graph.response;

import com.project.graph.model.Graph;
import com.project.graph.model.GraphForList;
import com.project.graph.model.Matrix;

public class ResponseMessage {
    private String message;
    private String status;

    private Matrix adjacencyMatrix;
    private Matrix incidenceMatrix;
    private GraphForList listOfAdjacencyOrIncidence;
    private Graph graph;

    private String path;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public void setOkStatus(){
        setStatus("OK");
        setMessage("the request was successful");
    }

    public void setBadRequestStatus(String param){
        setStatus("KO");
        setMessage("Param " + param + " has not value");
    }

    public void setError(String error){
        setStatus("KO");
        setMessage("Error:  " + error);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Matrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(Matrix adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public Matrix getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public void setIncidenceMatrix(Matrix incidenceMatrix) {
        this.incidenceMatrix = incidenceMatrix;
    }

    public GraphForList getListOfAdjacencyOrIncidence() {
        return listOfAdjacencyOrIncidence;
    }

    public void setListOfAdjacencyOrIncidence(GraphForList listOfAdjacencyOrIncidence) {
        this.listOfAdjacencyOrIncidence = listOfAdjacencyOrIncidence;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
