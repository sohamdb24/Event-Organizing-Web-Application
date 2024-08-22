package com.app.eventorganizer.controller;

import com.app.eventorganizer.dto.VendorEventServiceDTO;
import com.app.eventorganizer.service.VendorEventServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/vendor-event-services")
public class VendorEventServiceController {

    @Autowired
    private VendorEventServiceService vendorEventServiceService;

    @GetMapping("/{id}")
    public ResponseEntity<VendorEventServiceDTO> getVendorEventServiceById(@PathVariable Long id) {
        VendorEventServiceDTO vendorEventServiceDTO = vendorEventServiceService.getVendorEventServiceById(id);
        return ResponseEntity.ok(vendorEventServiceDTO);
    }

    @GetMapping
    public ResponseEntity<List<VendorEventServiceDTO>> getAllVendorEventServices() {
        List<VendorEventServiceDTO> vendorEventServiceList = vendorEventServiceService.getAllVendorEventServices();
        return ResponseEntity.ok(vendorEventServiceList);
    }

    @PostMapping
    public ResponseEntity<VendorEventServiceDTO> createVendorEventService(@RequestBody VendorEventServiceDTO vendorEventServiceDTO) {
        VendorEventServiceDTO createdVendorEventService = vendorEventServiceService.createVendorEventService(vendorEventServiceDTO);
        return ResponseEntity.ok(createdVendorEventService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorEventServiceDTO> updateVendorEventService(@PathVariable Long id, @RequestBody VendorEventServiceDTO vendorEventServiceDTO) {
        VendorEventServiceDTO updatedVendorEventService = vendorEventServiceService.updateVendorEventService(id, vendorEventServiceDTO);
        return ResponseEntity.ok(updatedVendorEventService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorEventService(@PathVariable Long id) {
        vendorEventServiceService.deleteVendorEventService(id);
        return ResponseEntity.noContent().build();
    }
}
