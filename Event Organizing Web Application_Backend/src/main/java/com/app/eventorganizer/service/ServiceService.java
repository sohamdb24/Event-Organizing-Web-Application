package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.ServiceDTO;
import java.util.List;

public interface ServiceService {
    ServiceDTO createService(ServiceDTO serviceDTO);
    List<ServiceDTO> getAllServices();
    ServiceDTO getServiceById(Long serviceId);
    ServiceDTO updateService(Long serviceId, ServiceDTO serviceDTO);
    void deleteService(Long serviceId);
}
