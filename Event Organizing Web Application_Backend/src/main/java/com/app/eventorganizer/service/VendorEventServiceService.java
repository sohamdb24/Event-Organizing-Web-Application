package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.VendorEventServiceDTO;

import java.util.List;

public interface VendorEventServiceService {
    VendorEventServiceDTO createVendorEventService(VendorEventServiceDTO vendorEventServiceDTO);
    List<VendorEventServiceDTO> getAllVendorEventServices();
    VendorEventServiceDTO getVendorEventServiceById(Long vendorEventServiceId);
    VendorEventServiceDTO updateVendorEventService(Long vendorEventServiceId, VendorEventServiceDTO vendorEventServiceDTO);
    void deleteVendorEventService(Long vendorEventServiceId);
}
