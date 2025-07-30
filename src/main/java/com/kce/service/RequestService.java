package com.kce.service;

import com.kce.entity.Request;

import java.util.List;

public interface RequestService {

    Request submitRequest(Request request); // For student to submit a request
    List<Request> getRequestsByRegisterNumber(String registerNumber); // Student-specific view
    List<Request> getAllRequests(); // Admin view
    Request markRequestAsCompleted(String requestId);
    List<Request> getRequestsByStatus(String status);
    List<Request> getRequestsByPriority(String priority);
    Request markRequestAsInProgress(String requestId);

}
