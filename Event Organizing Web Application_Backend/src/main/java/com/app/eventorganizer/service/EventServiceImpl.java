package com.app.eventorganizer.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.eventorganizer.dto.EventDTO;
import com.app.eventorganizer.entity.Event;
import com.app.eventorganizer.entity.EventType;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        lock.lock();
        try {
            Event event = new Event();
            event.setEventName(eventDTO.getEventName());
            event.setEventDescription(eventDTO.getEventDescription());
            event.setBasePrice(eventDTO.getBasePrice());
            event.setDuration(eventDTO.getDuration());
            event.setEventType(EventType.valueOf(eventDTO.getEventType()));
            event.setMaxGuests(eventDTO.getMaxGuests());
            event.setCreatedAt(LocalDateTime.now());
            event.setUpdatedAt(LocalDateTime.now());

            Event savedEvent = eventRepository.save(event);
            return mapToDTO(savedEvent);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        return mapToDTO(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        lock.lock();
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));

            event.setEventName(eventDTO.getEventName());
            event.setEventDescription(eventDTO.getEventDescription());
            event.setBasePrice(eventDTO.getBasePrice());
            event.setDuration(eventDTO.getDuration());
            event.setEventType(EventType.valueOf(eventDTO.getEventType()));
            event.setMaxGuests(eventDTO.getMaxGuests());
            event.setUpdatedAt(LocalDateTime.now());

            Event updatedEvent = eventRepository.save(event);
            return mapToDTO(updatedEvent);
        } finally {
            lock.unlock();
        }
    }

    private EventDTO mapToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setEventDescription(event.getEventDescription());
        eventDTO.setBasePrice(event.getBasePrice());
        eventDTO.setDuration(event.getDuration());
        eventDTO.setEventType(event.getEventType().name().toUpperCase());
        eventDTO.setMaxGuests(event.getMaxGuests());
        eventDTO.setCreatedAt(event.getCreatedAt());
        eventDTO.setUpdatedAt(event.getUpdatedAt());
        return eventDTO;
    }
}
