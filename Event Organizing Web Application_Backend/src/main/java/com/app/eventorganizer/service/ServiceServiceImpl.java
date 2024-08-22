package com.app.eventorganizer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.eventorganizer.dto.ServiceDTO;
import com.app.eventorganizer.entity.Services;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.ServiceRepository;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        lock.lock();
        try {
            Services service = new Services();
            service.setServiceName(serviceDTO.getServiceName());
            service.setServiceDescription(serviceDTO.getServiceDescription());
            service.setBasePrice(serviceDTO.getBasePrice());
            service.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            service.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Services savedService = serviceRepository.save(service);
            return mapToDTO(savedService);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceDTO> getAllServices() {
        lock.lock();
        try {
            return serviceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDTO getServiceById(Long serviceId) {
        lock.lock();
        try {
            Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + serviceId));
            return mapToDTO(service);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public ServiceDTO updateService(Long serviceId, ServiceDTO serviceDTO) {
        lock.lock();
        try {
            Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + serviceId));

            service.setServiceName(serviceDTO.getServiceName());
            service.setServiceDescription(serviceDTO.getServiceDescription());
            service.setBasePrice(serviceDTO.getBasePrice());
            service.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Services updatedService = serviceRepository.save(service);
            return mapToDTO(updatedService);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void deleteService(Long serviceId) {
        lock.lock();
        try {
            Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + serviceId));
            serviceRepository.delete(service);
        } finally {
            lock.unlock();
        }
    }

    private ServiceDTO mapToDTO(Services service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setServiceId(service.getServiceId());
        serviceDTO.setServiceName(service.getServiceName());
        serviceDTO.setServiceDescription(service.getServiceDescription());
        serviceDTO.setBasePrice(service.getBasePrice());
        serviceDTO.setCreatedAt(service.getCreatedAt());
        serviceDTO.setUpdatedAt(service.getUpdatedAt());
        return serviceDTO;
    }
}
