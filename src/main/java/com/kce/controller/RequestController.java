package com.kce.controller;

import com.kce.entity.Request;
import com.kce.exception.RequestNotFoundException;
import com.kce.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin("http://localhost:3000")
public class RequestController {

    private RequestService requestService;


    public RequestController(@Autowired RequestService requestService) {
        this.requestService = requestService;
    }

    // 1. Submit request (Student)
    @PostMapping("/create")
    public ResponseEntity<Request> submitRequest(@RequestBody Request request) {
        Request createdRequest = requestService.submitRequest(request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/student/{registerNumber}")
    public ResponseEntity<List<Request>> getRequestsByRegisterNumber(@PathVariable String registerNumber) {
        List<Request> requests = requestService.getRequestsByRegisterNumber(registerNumber);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<Request> resolveRequest(@PathVariable String id) {
        try {
            Request updatedRequest = requestService.markRequestAsCompleted(id);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (RequestNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        List<Request> requests = requestService.getRequestsByStatus(status);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Request>> getRequestsByPriority(@PathVariable String priority) {
        List<Request> requests = requestService.getRequestsByPriority(priority);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }


    @PutMapping("/markAsRead/{id}")
    public ResponseEntity<Request> markRequestAsInProgress(@PathVariable String id) {
        try {
            Request updatedRequest = requestService.markRequestAsInProgress(id);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (RequestNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
