package com.project.graph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.graph.model.GraphForList;
import com.project.graph.model.Matrix;
import com.project.graph.response.ResponseMessage;
import com.project.graph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphRestController {
    @Autowired
    GraphService graphService;

    @GetMapping("/add")
    public ResponseEntity<ResponseMessage> add(
            @RequestParam(name = "matrix", required = false) String matrix) {
        ResponseMessage responseMessage = new ResponseMessage();
        Matrix graphMatrix = new Matrix();
        try {
            graphMatrix = new ObjectMapper().readValue(matrix, Matrix.class);
            responseMessage = graphService.buildGraph(graphMatrix);
        }
        catch (Exception e){
            responseMessage.setError(e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/addList")
    public ResponseEntity<ResponseMessage> addList(
            @RequestParam(name = "matrix", required = false) String graphList) {
        ResponseMessage responseMessage = new ResponseMessage();
        GraphForList graph = new GraphForList();
        try {
            graph = new ObjectMapper().readValue(graphList, GraphForList.class);
            responseMessage = graphService.buildGraph(graph);
        }
        catch (Exception e){
            responseMessage.setError(e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}
