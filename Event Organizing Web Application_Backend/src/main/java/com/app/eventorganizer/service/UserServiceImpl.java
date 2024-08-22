package com.app.eventorganizer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.eventorganizer.dto.UserDTO;
import com.app.eventorganizer.entity.Role;
import com.app.eventorganizer.entity.User;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.repository.RoleRepository;
import com.app.eventorganizer.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final ReentrantLock lock = new ReentrantLock(); // Lock for concurrency

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        lock.lock();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return new UserDTO(user);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO) {
        lock.lock();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));


            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setAddress(userDTO.getAddress());
            user.setMobileNo(userDTO.getMobileNo());
            user.setGstNo(userDTO.getGstNo());
            user.setBookingCapacity(userDTO.getBookingCapacity());
            user.setSpecialization(userDTO.getSpecialization());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        lock.lock();
        try {
            userRepository.deleteById(id);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllVendors() {
        lock.lock();
        try {
            Role vendorRole = roleRepository.findByRoleName("VENDOR");
            List<User> vendors = userRepository.findByRole(vendorRole);
            return vendors.stream().map(UserDTO::new).collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getVendorById(Long id) {
        lock.lock();
        try {
            Role vendorRole = roleRepository.findByRoleName("VENDOR");
            User vendor = userRepository.findByUserIdAndRole(id, vendorRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
            return new UserDTO(vendor);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void updateVendor(Long id, UserDTO userDTO) {
        lock.lock();
        try {
            Role vendorRole = roleRepository.findByRoleName("VENDOR");
            User vendor = userRepository.findByUserIdAndRole(id, vendorRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));


            vendor.setFirstName(userDTO.getFirstName());
            vendor.setLastName(userDTO.getLastName());
            vendor.setEmail(userDTO.getEmail());
            vendor.setAddress(userDTO.getAddress());
            vendor.setMobileNo(userDTO.getMobileNo());
            vendor.setGstNo(userDTO.getGstNo());
            vendor.setBookingCapacity(userDTO.getBookingCapacity());
            vendor.setSpecialization(userDTO.getSpecialization());
            vendor.setDateOfBirth(userDTO.getDateOfBirth());
            vendor.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(vendor);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void deleteVendor(Long id) {
        lock.lock();
        try {
            Role vendorRole = roleRepository.findByRoleName("VENDOR");
            User vendor = userRepository.findByUserIdAndRole(id, vendorRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
            userRepository.delete(vendor);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllCustomers() {
        lock.lock();
        try {
            Role customerRole = roleRepository.findByRoleName("CUSTOMER");
            List<User> customers = userRepository.findByRole(customerRole);
            return customers.stream().map(UserDTO::new).collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getCustomerById(Long id) {
        lock.lock();
        try {
            Role customerRole = roleRepository.findByRoleName("CUSTOMER");
            User customer = userRepository.findByUserIdAndRole(id, customerRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            return new UserDTO(customer);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, UserDTO userDTO) {
        lock.lock();
        try {
            Role customerRole = roleRepository.findByRoleName("CUSTOMER");
            User customer = userRepository.findByUserIdAndRole(id, customerRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


            customer.setFirstName(userDTO.getFirstName());
            customer.setLastName(userDTO.getLastName());
            customer.setEmail(userDTO.getEmail());
            customer.setAddress(userDTO.getAddress());
            customer.setMobileNo(userDTO.getMobileNo());
            customer.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(customer);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        lock.lock();
        try {
            Role customerRole = roleRepository.findByRoleName("CUSTOMER");
            User customer = userRepository.findByUserIdAndRole(id, customerRole)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            userRepository.delete(customer);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllAdmins() {
        lock.lock();
        try {
            Role adminRole = roleRepository.findByRoleName("ADMIN");
            List<User> admins = userRepository.findByRole(adminRole);
            return admins.stream().map(UserDTO::new).collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }
}
