package com.app.eventorganizer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.eventorganizer.dto.BookingDTO;
import com.app.eventorganizer.entity.Booking;
import com.app.eventorganizer.entity.BookingStatus;
import com.app.eventorganizer.entity.User;
import com.app.eventorganizer.entity.VendorEvent;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.BookingRepository;
import com.app.eventorganizer.repository.UserRepository;
import com.app.eventorganizer.repository.VendorEventRepository;


import com.app.eventorganizer.validator.BookingValidator;



@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VendorEventRepository vendorEventRepository;
    private final Lock lock = new ReentrantLock(); 

    @Autowired
    public BookingServiceImpl(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            VendorEventRepository vendorEventRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vendorEventRepository = vendorEventRepository;
    }

    @Override
    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        lock.lock(); 
        try {
            
            BookingValidator.validateBooking(bookingDTO);
            
            Booking booking = convertToEntity(bookingDTO);
            booking = bookingRepository.save(booking);
            return convertToDTO(booking);
        } finally {
            lock.unlock(); 
        }
    }


    @Override
    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserUserId(userId);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user ID: " + userId);
        }

        return bookings.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO) {
        lock.lock(); 
        try {
            BookingValidator.validateBooking(bookingDTO);

            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
            updateEntityWithDTO(booking, bookingDTO);
            booking = bookingRepository.save(booking);
            return convertToDTO(booking);
        } finally {
            lock.unlock(); 
        }
    }


    @Override
    @Transactional
    public void deleteBooking(Long bookingId) {
        lock.lock(); 
        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
            bookingRepository.delete(booking);
        } finally {
            lock.unlock(); 
        }
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setVendorEventId(booking.getVendorEvent().getVendorEventId());
        dto.setLocation(booking.getLocation());
        dto.setEventDate(booking.getEventDate());  
        dto.setNumberOfGuests(booking.getNumberOfGuests());
        dto.setBookingStatus(booking.getBookingStatus().name());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setAdvancePayment(booking.getAdvancePayment());
        dto.setRemainingAmount(booking.getRemainingAmount());
        dto.setCreatedAt(booking.getCreatedAt().toLocalDateTime());  
        dto.setUpdatedAt(booking.getUpdatedAt().toLocalDateTime());  
        return dto;
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
  
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingDTO.getUserId()));
        VendorEvent vendorEvent = vendorEventRepository.findById(bookingDTO.getVendorEventId())
                .orElseThrow(() -> new ResourceNotFoundException("VendorEvent not found with id: " + bookingDTO.getVendorEventId()));

       
        booking.setUser(user);
        booking.setVendorEvent(vendorEvent);
        booking.setLocation(bookingDTO.getLocation());
        booking.setEventDate(bookingDTO.getEventDate());  
        booking.setNumberOfGuests(bookingDTO.getNumberOfGuests());
        booking.setBookingStatus(BookingStatus.valueOf(bookingDTO.getBookingStatus()));
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setAdvancePayment(bookingDTO.getAdvancePayment());
        booking.setRemainingAmount(bookingDTO.getRemainingAmount());
        booking.setCreatedAt(Timestamp.valueOf(bookingDTO.getCreatedAt())); 
        booking.setUpdatedAt(Timestamp.valueOf(bookingDTO.getUpdatedAt()));
        return booking;
    }

    private void updateEntityWithDTO(Booking booking, BookingDTO bookingDTO) {
        booking.setLocation(bookingDTO.getLocation());
        booking.setEventDate(bookingDTO.getEventDate());  
        booking.setNumberOfGuests(bookingDTO.getNumberOfGuests());
        booking.setBookingStatus(BookingStatus.valueOf(bookingDTO.getBookingStatus()));
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setAdvancePayment(bookingDTO.getAdvancePayment());
        booking.setRemainingAmount(bookingDTO.getRemainingAmount());
    }
}
