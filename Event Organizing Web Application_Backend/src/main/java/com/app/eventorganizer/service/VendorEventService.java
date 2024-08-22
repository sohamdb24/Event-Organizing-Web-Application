package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.VendorEventDTO;

import java.util.List;

public interface VendorEventService {
    VendorEventDTO createVendorEvent(VendorEventDTO vendorEventDTO);
    List<VendorEventDTO> getAllVendorEvents();
    VendorEventDTO getVendorEventById(Long vendorEventId);
    VendorEventDTO updateVendorEvent(Long vendorEventId, VendorEventDTO vendorEventDTO);
    void deleteVendorEvent(Long vendorEventId);
}
