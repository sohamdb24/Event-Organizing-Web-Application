package com.app.eventorganizer.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;


@Data
public class VendorEventServiceDTO {
    private Long vendorEventServiceId;
    private Long vendorEventId;
    private Long serviceId;
    private BigDecimal servicePrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
