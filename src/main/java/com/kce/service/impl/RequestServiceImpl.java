package com.kce.service.impl;

import com.kce.entity.Request;
import com.kce.exception.RequestNotFoundException;
import com.kce.repository.RequestRepository;
import com.kce.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;


    public RequestServiceImpl(@Autowired RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request submitRequest(Request request) {
        request.setStatus("Pending"); // default status on submission
        return requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestsByRegisterNumber(String registerNumber) {
        return requestRepository.findByRegisterNumber(registerNumber);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }
    @Override
    public Request markRequestAsCompleted(String requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus("Resolved");
            request.setResponseMessage("Your problem is sorted out");
            return requestRepository.save(request);
        } else {
            throw new RequestNotFoundException("Request not found for ID: " + requestId);
        }
    }

    @Override
    public Request markRequestAsInProgress(String requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus("In Progress");
            request.setResponseMessage("Your issue will be sorted out soon");
            return requestRepository.save(request);
        } else {
            throw new RequestNotFoundException("Request not found for ID: " + requestId);
        }
    }



    @Override
    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public List<Request> getRequestsByPriority(String priority) {
        return requestRepository.findByPriority(priority);
    }



}
