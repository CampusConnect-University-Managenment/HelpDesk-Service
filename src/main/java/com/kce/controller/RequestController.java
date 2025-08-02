package com.kce.controller;

import com.kce.entity.Request;
import com.kce.exception.RequestNotFoundException;
import com.kce.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin("http://localhost:3000")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public RequestController(@Autowired RequestService requestService) {
        this.requestService = requestService;
    }

    // 1. Submit request (Student)
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<Request> createRequest(
            @RequestPart("name") String name,
            @RequestPart("registerNumber") String registerNumber,
            @RequestPart("department") String department,
            @RequestPart("type") String type,
            @RequestPart("location") String location,
            // @RequestPart("priority") String priority, // Uncomment if needed
            @RequestPart("subject") String subject,
            @RequestPart("description") String description,
            @RequestPart("status") String status,
            @RequestPart("createdAt") String createdAt,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        Request request = new Request();
        request.setName(name);
        request.setRegisterNumber(registerNumber);
        request.setDepartment(department);
        request.setType(type);
        request.setLocation(location);
        // request.setPriority(priority); // Uncomment if priority is used
        request.setSubject(subject);
        request.setDescription(description);
        request.setStatus(status);
        request.setCreatedAt(LocalDateTime.parse(createdAt));


        if (imageFile != null && !imageFile.isEmpty()) {
            ObjectId fileId = gridFsTemplate.store(
                    imageFile.getInputStream(),
                    imageFile.getOriginalFilename(),
                    imageFile.getContentType()
            );
            request.setImagePath(fileId.toString());
        }

        Request saved = requestService.submitRequest(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 2. Get all requests for a student
    @GetMapping("/student/{registerNumber}")
    public ResponseEntity<List<Request>> getRequestsByRegisterNumber(@PathVariable String registerNumber) {
        List<Request> requests = requestService.getRequestsByRegisterNumber(registerNumber);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // 3. Get all requests (Admin)
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // 4. Mark request as Resolved
    @PutMapping("/resolve/{id}")
    public ResponseEntity<Request> resolveRequest(@PathVariable String id) {
        try {
            Request updatedRequest = requestService.markRequestAsCompleted(id);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (RequestNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 5. Get requests by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        List<Request> requests = requestService.getRequestsByStatus(status);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // 6. Mark as In Progress
    @PutMapping("/markAsRead/{id}")
    public ResponseEntity<Request> markRequestAsInProgress(@PathVariable String id) {
        try {
            Request updatedRequest = requestService.markRequestAsInProgress(id);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (RequestNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 7. Fetch image from GridFS
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(id))));
        if (file == null) return ResponseEntity.notFound().build();

        GridFsResource resource = gridFsTemplate.getResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .body(resource.getInputStream().readAllBytes());
    }
}
