package com.app.eventorganizer.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ServiceDTO {
    private Long serviceId;
    private String serviceName;
    private String serviceDescription;
    private BigDecimal basePrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
