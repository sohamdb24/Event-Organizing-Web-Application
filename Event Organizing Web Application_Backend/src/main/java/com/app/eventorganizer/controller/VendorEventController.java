package com.app.eventorganizer.controller;

import com.app.eventorganizer.dto.VendorEventDTO;
import com.app.eventorganizer.service.VendorEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/vendor-events")
public class VendorEventController {

    @Autowired
    private VendorEventService vendorEventService;

    @GetMapping("/{id}")
    public ResponseEntity<VendorEventDTO> getVendorEventById(@PathVariable Long id) {
        VendorEventDTO vendorEventDTO = vendorEventService.getVendorEventById(id);
        return ResponseEntity.ok(vendorEventDTO);
    }

    @GetMapping
    public ResponseEntity<List<VendorEventDTO>> getAllVendorEvents() {
        List<VendorEventDTO> vendorEventList = vendorEventService.getAllVendorEvents();
        return ResponseEntity.ok(vendorEventList);
    }

    @PostMapping
    public ResponseEntity<VendorEventDTO> createVendorEvent(@RequestBody VendorEventDTO vendorEventDTO) {
        VendorEventDTO createdVendorEvent = vendorEventService.createVendorEvent(vendorEventDTO);
        return ResponseEntity.ok(createdVendorEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorEventDTO> updateVendorEvent(@PathVariable Long id, @RequestBody VendorEventDTO vendorEventDTO) {
        VendorEventDTO updatedVendorEvent = vendorEventService.updateVendorEvent(id, vendorEventDTO);
        return ResponseEntity.ok(updatedVendorEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorEvent(@PathVariable Long id) {
        vendorEventService.deleteVendorEvent(id);
        return ResponseEntity.noContent().build();
    }
}
