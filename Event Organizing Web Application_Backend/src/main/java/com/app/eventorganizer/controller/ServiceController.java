package com.app.eventorganizer.controller;

import com.app.eventorganizer.dto.ServiceDTO;
import com.app.eventorganizer.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/create")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.createService(serviceDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable("id") Long id, @RequestBody ServiceDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
