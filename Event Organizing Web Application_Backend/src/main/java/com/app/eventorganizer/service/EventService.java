package com.app.eventorganizer.service;

import java.util.List;
import com.app.eventorganizer.dto.EventDTO;

public interface EventService {
    EventDTO createEvent(EventDTO eventDTO);
    List<EventDTO> getAllEvents();
    EventDTO getEventById(Long eventId);
    void deleteEvent(Long eventId);
    EventDTO updateEvent(Long eventId, EventDTO eventDTO);
}
