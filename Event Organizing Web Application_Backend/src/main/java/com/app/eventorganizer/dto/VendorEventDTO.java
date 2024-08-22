package com.app.eventorganizer.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class VendorEventDTO {
    private Long vendorEventId;
    private Long vendorId;
    private Long eventId;
    private BigDecimal customPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    
}
