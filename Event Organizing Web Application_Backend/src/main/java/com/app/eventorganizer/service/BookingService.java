package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.BookingDTO;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getBookingById(Long bookingId);
    List<BookingDTO> getAllBookings();
    BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO);
    void deleteBooking(Long bookingId);
    List<BookingDTO> getBookingsByUserId(Long userId);
}
