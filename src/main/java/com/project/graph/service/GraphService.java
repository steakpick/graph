package com.project.graph.service;

import com.project.graph.model.*;
import com.project.graph.response.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphService {
    public ResponseMessage buildGraph(Matrix matrix) {
        //if count of edges equal null then it is adjacency matrix
        //else it is incidence matrix
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setOkStatus();

        Graph graph;
        FindPathService findPathService = new FindPathService();
        if (matrix.getCountOfEdge() == null) {
            graph = buildGraphByAdjacencyMatrix(matrix);
        } else {
            graph = buildGraphByIncidenceMatrix(matrix);
        }
        responseMessage.setGraph(graph);
        responseMessage.setAdjacencyMatrix(disassembleGraphToAdjacencyMatrix(graph));
        responseMessage.setIncidenceMatrix(disassembleGraphToIncidenceMatrix(graph));
        responseMessage.setListOfAdjacencyOrIncidence(disassembleGraphToList(graph));
        responseMessage.setPath(findPathService.findPath(graph));

        return responseMessage;
    }

    public ResponseMessage buildGraph(GraphForList graphForList) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setOkStatus();
        FindPathService findPathService = new FindPathService();
        Graph graph = buildGraphByList(graphForList);

        responseMessage.setGraph(graph);
        responseMessage.setAdjacencyMatrix(disassembleGraphToAdjacencyMatrix(graph));
        responseMessage.setIncidenceMatrix(disassembleGraphToIncidenceMatrix(graph));
        responseMessage.setListOfAdjacencyOrIncidence(disassembleGraphToList(graph));
        responseMessage.setPath(findPathService.findPath(graph));

        return responseMessage;
    }

    public Graph buildGraphByList(GraphForList graphForList) {
        Graph graph = new Graph();
        List<Vertex> vertexList = new ArrayList<>();

        for (VertexForList vertexForList : graphForList.getVertex()) {
            Vertex vertex = new Vertex();
            vertex.setName(vertexForList.getName());

            List<Edge> edgeList = new ArrayList<>();
            for (VertexForList.Edge edgeForList : vertexForList.getEdgeList()) {
                Edge edge = new Edge();

                edge.setName(String.valueOf(edgeForList.getCost()));
                edge.setFirstVertex(vertexForList.getName());
                edge.setSecondVertex(String.valueOf(edgeForList.getV()));

                edgeList.add(edge);
            }

            vertex.setEdges(edgeList);
            vertexList.add(vertex);
        }

        graph.setVertexList(vertexList);
        normalizeListGraph(graph);

        return graph;
    }

    private GraphForList disassembleGraphToList(Graph graph) {
        GraphForList graphForList = new GraphForList();
        List<VertexForList> vertexForListList = new ArrayList<>();

        for (Vertex vertex : graph.getVertexList()) {
            VertexForList vertexForList = new VertexForList();
            vertexForList.setName(vertex.getName());

            List<VertexForList.Edge> edgeList = new ArrayList<>();
            for (Edge edge : vertex.getEdges()) {
                VertexForList.Edge edgeForList = new VertexForList.Edge();

                edgeForList.setCost(Integer.valueOf(edge.getName()));
                edgeForList.setV(Integer.valueOf(edge.getSecondVertex()));

                edgeList.add(edgeForList);
            }

            vertexForList.setEdgeList(edgeList);
            vertexForListList.add(vertexForList);
        }

        graphForList.setVertex(vertexForListList);
        return graphForList;
    }

    private Graph buildGraphByAdjacencyMatrix(Matrix matrix) {
        Graph graph = new Graph();

        int countOfVertex = matrix.getCountOfVertex();
        Integer[][] matrixValues = matrix.getMatrix();
        List<Vertex> vertexList = new ArrayList<>();

        for (int i = 0; i < countOfVertex; i++) {
            Vertex vertex = new Vertex();
            vertex.setName(String.valueOf(i));

            List<Edge> edgeList = new ArrayList<>();
            for (int j = 0; j < countOfVertex; j++) {
                if (matrixValues[i][j] != 0) {
                    Edge edge = new Edge();
                    edge.setDirected(!(matrixValues[i][j] == matrixValues[j][i]));
                    if (edge.isDirected()) {
                        if (matrixValues[i][j] != 0) {
                            edge.setDirectedVertex(String.valueOf(j));
                        } else if (matrixValues[j][i] != 0) {
                            edge.setDirectedVertex(String.valueOf(i));
                        }
                    }
                    edge.setName(String.valueOf(matrixValues[i][j]));
                    edge.setFirstVertex(String.valueOf(i));
                    edge.setSecondVertex(String.valueOf(j));

                    edgeList.add(edge);
                }
            }

            vertex.setEdges(edgeList);
            vertexList.add(vertex);
        }

        graph.setVertexList(vertexList);
        return graph;
    }

    private Graph buildGraphByIncidenceMatrix(Matrix matrix) {
        Graph graph = new Graph();

        int countOfVertex = matrix.getCountOfVertex();
        int countOfEdges = matrix.getCountOfEdge();
        Integer[][] matrixValues = matrix.getMatrix();
        List<Vertex> vertexList = new ArrayList<>();

        for (int i = 0; i < countOfVertex; i++) {
            Vertex vertex = new Vertex();
            vertex.setName(String.valueOf(i));

            List<Edge> edgeList = new ArrayList<>();
            for (int j = 0; j < countOfEdges; j++) {
                if (matrixValues[i][j] != 0) {
                    Edge edge = new Edge();

                    if (matrixValues[i][j] < 0) {
                        edge.setDirected(true);
                        edge.setDirectedVertex(String.valueOf(i));
                    }

                    edge.setDirected(matrixValues[i][j] < 0);
                    edge.setName(String.valueOf(Math.abs(matrixValues[i][j])));
                    edge.setFirstVertex(String.valueOf(i));

                    int secondVertex = 0;
                    for (int k = 0; k < countOfVertex; k++) {
                        if (k == i) continue;
                        if (matrixValues[k][j] != 0) {
                            secondVertex = k;
                            if (matrixValues[k][j] < 0) {
                                edge.setDirected(true);
                                edge.setDirectedVertex(String.valueOf(secondVertex));
                            }
                            break;
                        }
                    }
                    edge.setSecondVertex(String.valueOf(secondVertex));

                    edgeList.add(edge);
                }
            }

            vertex.setEdges(edgeList);
            vertexList.add(vertex);
        }

        graph.setVertexList(vertexList);

        return graph;
    }

    private Matrix disassembleGraphToAdjacencyMatrix(Graph graph) {
        Matrix matrix = new Matrix();

        int countOfVertex = graph.getVertexList().size();
        Integer[][] matrixOfGraph = new Integer[countOfVertex][countOfVertex];
        fillMatrixOfZeroValue(matrixOfGraph, countOfVertex, countOfVertex);

        for (Vertex vertex : graph.getVertexList()) {
            for (Edge edge : vertex.getEdges()) {
                if (edge.getDirectedVertex() == null || edge.getDirectedVertex().isEmpty()) {
                    matrixOfGraph[Integer.valueOf(edge.getFirstVertex())][Integer.valueOf(edge.getSecondVertex())] = Integer.valueOf(edge.getName());
                    matrixOfGraph[Integer.valueOf(edge.getSecondVertex())][Integer.valueOf(edge.getFirstVertex())] = Integer.valueOf(edge.getName());
                } else if (edge.getFirstVertex().equals(edge.getDirectedVertex())) {
                    matrixOfGraph[Integer.valueOf(edge.getFirstVertex())][Integer.valueOf(edge.getSecondVertex())] = 0;
                    matrixOfGraph[Integer.valueOf(edge.getSecondVertex())][Integer.valueOf(edge.getFirstVertex())] = Integer.valueOf(edge.getName());
                } else if (edge.getSecondVertex().equals(edge.getDirectedVertex())) {
                    matrixOfGraph[Integer.valueOf(edge.getFirstVertex())][Integer.valueOf(edge.getSecondVertex())] = Integer.valueOf(edge.getName());
                    matrixOfGraph[Integer.valueOf(edge.getSecondVertex())][Integer.valueOf(edge.getFirstVertex())] = 0;
                }
            }
        }

        matrix.setCountOfVertex(countOfVertex);
        matrix.setMatrix(matrixOfGraph);
        return matrix;
    }

    private Matrix disassembleGraphToIncidenceMatrix(Graph graph) {
        Matrix matrix = new Matrix();

        int countOfVertex = graph.getVertexList().size();
        int countOfEdges = getCountOfEdges(graph);
        Integer[][] matrixOfGraph = new Integer[countOfVertex][countOfEdges];
        fillMatrixOfZeroValue(matrixOfGraph, countOfVertex, countOfEdges);

        List<Edge> edgeList = getFilteringEdges(graph);

        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            matrixOfGraph[Integer.valueOf(edge.getFirstVertex())][i] = edge.getDirectedVertex().equals(edge.getFirstVertex())
                    ? Integer.valueOf("-" + edge.getName().replace("-", "")) : Integer.valueOf(edge.getName());
            matrixOfGraph[Integer.valueOf(edge.getSecondVertex())][i] = edge.getDirectedVertex().equals(edge.getSecondVertex())
                    ? Integer.valueOf("-" + edge.getName().replace("-", "")) : Integer.valueOf(edge.getName());
        }

        matrix.setCountOfVertex(countOfVertex);
        matrix.setCountOfEdge(countOfEdges);
        matrix.setMatrix(matrixOfGraph);
        showMatrix(matrix, "A");

        return matrix;
    }

    private void fillMatrixOfZeroValue(Integer[][] matrix, int countOfVertex, int countOfEdges) {
        for (int i = 0; i < countOfVertex; i++)
            for (int j = 0; j < countOfEdges; j++)
                matrix[i][j] = 0;
    }

    private void showMatrix(Matrix matrix, String name) {
        int countOfVertex = matrix.getCountOfVertex();
        int countOfEdges = matrix.getCountOfEdge() != null ? matrix.getCountOfEdge() : countOfVertex;
        Integer[][] matrixOfGraph = matrix.getMatrix();
        System.out.println(name + " matrix:");
        for (int i = 0; i < countOfVertex; i++) {
            String row = "[";
            for (int j = 0; j < countOfEdges; j++)
                if (j != countOfEdges - 1) row += "\t" + matrixOfGraph[i][j] + ", ";
                else row += "\t" + matrixOfGraph[i][j] + "]";
            if (i != countOfVertex - 1) System.out.println(row + ",");
            else System.out.println(row);
        }

        System.out.println();
    }

    private int getCountOfEdges(Graph graph) {
        List<Edge> edgeList = new ArrayList<>();

        for (Vertex vertex : graph.getVertexList()) {
            for (Edge edge : vertex.getEdges()) {
                if (!containsEdge(edgeList, edge)) edgeList.add(edge);
            }
        }

        return edgeList.size();
    }

    private List<Edge> getFilteringEdges(Graph graph) {
        List<Edge> edgeList = new ArrayList<>();

        for (Vertex vertex : graph.getVertexList()) {
            for (Edge edge : vertex.getEdges()) {
                if (!containsEdge(edgeList, edge)) edgeList.add(edge);
            }
        }

        return edgeList;
    }

    private void normalizeListGraph(Graph graph) {
        List<Edge> edgeList = new ArrayList<>();

        for (Vertex vertex : graph.getVertexList()) {
            for (Edge edge : vertex.getEdges()) {
                edgeList.add(edge);
            }
        }

        for (Vertex vertex : graph.getVertexList()) {
            for (Edge edge : vertex.getEdges()) {
                if (!containsEdge(edgeList, edge)) {
                    edge.setDirected(true);
                    edge.setDirectedVertex(edge.getSecondVertex());
                }
            }
        }
    }

    private boolean containsEdge(List<Edge> edgeList, Edge edge) {
        for (Edge e : edgeList) {
            if (e.isRevertEdge(edge)) return true;
        }
        return false;
    }

}
