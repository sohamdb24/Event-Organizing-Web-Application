package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.VendorEventDTO;
import com.app.eventorganizer.entity.Event;
import com.app.eventorganizer.entity.User;
import com.app.eventorganizer.entity.VendorEvent;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.EventRepository;
import com.app.eventorganizer.repository.UserRepository;
import com.app.eventorganizer.repository.VendorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorEventServiceImpl implements VendorEventService {

    @Autowired
    private VendorEventRepository vendorEventRepository;
    
    @Autowired
    private UserRepository userRepository;  
    
    @Autowired
    private EventRepository eventRepository;  

    private final Object lock = new Object();

    @Override
    @Transactional
    public VendorEventDTO createVendorEvent(VendorEventDTO vendorEventDTO) {
        synchronized (lock) {
            User vendor = userRepository.findById(vendorEventDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + vendorEventDTO.getVendorId()));
            Event event = eventRepository.findById(vendorEventDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + vendorEventDTO.getEventId()));
            
            VendorEvent vendorEvent = new VendorEvent();
            vendorEvent.setVendor(vendor);
            vendorEvent.setEvent(event);
            vendorEvent.setCustomPrice(vendorEventDTO.getCustomPrice());
            vendorEvent.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            vendorEvent.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            VendorEvent savedVendorEvent = vendorEventRepository.save(vendorEvent);
            return mapToDTO(savedVendorEvent);
        }
    }

    @Override
    @Transactional(readOnly = true) 
    public List<VendorEventDTO> getAllVendorEvents() {
        return vendorEventRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) 
    public VendorEventDTO getVendorEventById(Long vendorEventId) {
        VendorEvent vendorEvent = vendorEventRepository.findById(vendorEventId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Event not found with id " + vendorEventId));
        return mapToDTO(vendorEvent);
    }

    @Override
    @Transactional
    public VendorEventDTO updateVendorEvent(Long vendorEventId, VendorEventDTO vendorEventDTO) {
        synchronized (lock) {
            VendorEvent vendorEvent = vendorEventRepository.findById(vendorEventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event not found with id " + vendorEventId));

            User vendor = userRepository.findById(vendorEventDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + vendorEventDTO.getVendorId()));
            Event event = eventRepository.findById(vendorEventDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + vendorEventDTO.getEventId()));
            
            vendorEvent.setVendor(vendor);
            vendorEvent.setEvent(event);
            vendorEvent.setCustomPrice(vendorEventDTO.getCustomPrice());
            vendorEvent.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            VendorEvent updatedVendorEvent = vendorEventRepository.save(vendorEvent);
            return mapToDTO(updatedVendorEvent);
        }
    }

    @Override
    @Transactional
    public void deleteVendorEvent(Long vendorEventId) {
        synchronized (lock) {
            VendorEvent vendorEvent = vendorEventRepository.findById(vendorEventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor Event not found with id " + vendorEventId));
            vendorEventRepository.delete(vendorEvent);
        }
    }

    private VendorEventDTO mapToDTO(VendorEvent vendorEvent) {
        VendorEventDTO vendorEventDTO = new VendorEventDTO();
        vendorEventDTO.setVendorEventId(vendorEvent.getVendorEventId());
        vendorEventDTO.setVendorId(vendorEvent.getVendor().getUserId()); 
        vendorEventDTO.setEventId(vendorEvent.getEvent().getEventId());    
        vendorEventDTO.setCustomPrice(vendorEvent.getCustomPrice());
        vendorEventDTO.setCreatedAt(vendorEvent.getCreatedAt());
        vendorEventDTO.setUpdatedAt(vendorEvent.getUpdatedAt());
        return vendorEventDTO;
    }
}
