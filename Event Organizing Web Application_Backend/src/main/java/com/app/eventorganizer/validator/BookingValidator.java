package com.app.eventorganizer.validator;

import com.app.eventorganizer.dto.BookingDTO;
import com.app.eventorganizer.exception.ValidationException;

import java.math.BigDecimal;

public class BookingValidator {

    public static void validateBooking(BookingDTO booking) throws ValidationException {
        if (booking.getUserId() == null || booking.getVendorEventId() == null) {
            throw new ValidationException("User ID and Vendor Event ID cannot be null.");
        }
        if (booking.getLocation() == null || booking.getLocation().isEmpty()) {
            throw new ValidationException("Location cannot be empty.");
        }
        if (booking.getEventDate() == null) {
            throw new ValidationException("Event Date cannot be null.");
        }
        if (booking.getNumberOfGuests() <= 0) {
            throw new ValidationException("Number of Guests must be greater than zero.");
        }
        if (booking.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Total Amount cannot be negative.");
        }
        if (booking.getAdvancePayment().compareTo(BigDecimal.ZERO) < 0 || booking.getAdvancePayment().compareTo(booking.getTotalAmount()) > 0) {
            throw new ValidationException("Advance Payment must be between 0 and Total Amount.");
        }
        if (booking.getRemainingAmount().compareTo(BigDecimal.ZERO) < 0 || booking.getRemainingAmount().compareTo(booking.getTotalAmount()) > 0) {
            throw new ValidationException("Remaining Amount must be between 0 and Total Amount.");
        }
    }
}
