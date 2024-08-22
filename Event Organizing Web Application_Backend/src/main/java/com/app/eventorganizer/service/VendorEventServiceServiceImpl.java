package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.VendorEventServiceDTO;
import com.app.eventorganizer.entity.Services;
import com.app.eventorganizer.entity.VendorEvent;
import com.app.eventorganizer.entity.VendorEventService;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.ServiceRepository;
import com.app.eventorganizer.repository.VendorEventRepository;
import com.app.eventorganizer.repository.VendorEventServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service

public class VendorEventServiceServiceImpl implements VendorEventServiceService {

    @Autowired
    private VendorEventServiceRepository vendorEventServiceRepository;

    @Autowired
    private VendorEventRepository vendorEventRepository;

    @Autowired
    private ServiceRepository servicesRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public VendorEventServiceDTO createVendorEventService(VendorEventServiceDTO vendorEventServiceDTO) {
        lock.lock();
        try {
            VendorEventService vendorEventService = new VendorEventService();

  
            VendorEvent vendorEvent = vendorEventRepository.findById(vendorEventServiceDTO.getVendorEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event not found with id " + vendorEventServiceDTO.getVendorEventId()));
            
            Services service = servicesRepository.findById(vendorEventServiceDTO.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + vendorEventServiceDTO.getServiceId()));


            vendorEventService.setVendorEvent(vendorEvent);
            vendorEventService.setService(service);
            vendorEventService.setServicePrice(vendorEventServiceDTO.getServicePrice());
            vendorEventService.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            vendorEventService.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            VendorEventService savedVendorEventService = vendorEventServiceRepository.save(vendorEventService);
            return mapToDTO(savedVendorEventService);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true) 
    public List<VendorEventServiceDTO> getAllVendorEventServices() {
        return vendorEventServiceRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) 
    public VendorEventServiceDTO getVendorEventServiceById(Long vendorEventServiceId) {
        VendorEventService vendorEventService = vendorEventServiceRepository.findById(vendorEventServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Event Service not found with id " + vendorEventServiceId));
        return mapToDTO(vendorEventService);
    }

    @Override
    @Transactional
    public VendorEventServiceDTO updateVendorEventService(Long vendorEventServiceId, VendorEventServiceDTO vendorEventServiceDTO) {
        lock.lock();
        try {
            VendorEventService vendorEventService = vendorEventServiceRepository.findById(vendorEventServiceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event Service not found with id " + vendorEventServiceId));

          
            VendorEvent vendorEvent = vendorEventRepository.findById(vendorEventServiceDTO.getVendorEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event not found with id " + vendorEventServiceDTO.getVendorEventId()));

            Services service = servicesRepository.findById(vendorEventServiceDTO.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + vendorEventServiceDTO.getServiceId()));

            
            vendorEventService.setVendorEvent(vendorEvent);
            vendorEventService.setService(service);
            vendorEventService.setServicePrice(vendorEventServiceDTO.getServicePrice());
            vendorEventService.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            VendorEventService updatedVendorEventService = vendorEventServiceRepository.save(vendorEventService);
            return mapToDTO(updatedVendorEventService);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void deleteVendorEventService(Long vendorEventServiceId) {
        lock.lock();
        try {
            VendorEventService vendorEventService = vendorEventServiceRepository.findById(vendorEventServiceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event Service not found with id " + vendorEventServiceId));
            vendorEventServiceRepository.delete(vendorEventService);
        } finally {
            lock.unlock();
        }
    }

    private VendorEventServiceDTO mapToDTO(VendorEventService vendorEventService) {
        VendorEventServiceDTO vendorEventServiceDTO = new VendorEventServiceDTO();
        vendorEventServiceDTO.setVendorEventServiceId(vendorEventService.getVendorEventServiceId());
        vendorEventServiceDTO.setVendorEventId(vendorEventService.getVendorEvent().getVendorEventId());
        vendorEventServiceDTO.setServiceId(vendorEventService.getService().getServiceId());
        vendorEventServiceDTO.setServicePrice(vendorEventService.getServicePrice());
        vendorEventServiceDTO.setCreatedAt(vendorEventService.getCreatedAt());
        vendorEventServiceDTO.setUpdatedAt(vendorEventService.getUpdatedAt());
        return vendorEventServiceDTO;
    }
}
