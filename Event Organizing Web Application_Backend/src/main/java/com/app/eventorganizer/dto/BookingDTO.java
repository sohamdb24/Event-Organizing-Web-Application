package com.app.eventorganizer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private Long vendorEventId;
    private String location;
    private LocalDate eventDate;;
    private int numberOfGuests;
    private String bookingStatus;
    private BigDecimal totalAmount;
    private BigDecimal advancePayment;
    private BigDecimal remainingAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	}

