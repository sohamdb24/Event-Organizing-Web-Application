package com.app.eventorganizer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventDTO {
    private Long eventId;
    private String eventName;
    private String eventDescription;
    private BigDecimal basePrice;
    private Integer duration;
    private String eventType; 
    private Integer maxGuests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
