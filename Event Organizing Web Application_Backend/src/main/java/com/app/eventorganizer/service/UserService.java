package com.app.eventorganizer.service;

import java.util.List;
import com.app.eventorganizer.dto.UserDTO;

public interface UserService {


    UserDTO getUserById(Long id);


    void updateUser(Long id, UserDTO userDTO);


    void deleteUser(Long id);


    List<UserDTO> getAllVendors();


    UserDTO getVendorById(Long id);


    void updateVendor(Long id, UserDTO userDTO);


    void deleteVendor(Long id);


    List<UserDTO> getAllCustomers();


    UserDTO getCustomerById(Long id);


    void updateCustomer(Long id, UserDTO userDTO);


    void deleteCustomer(Long id);


    List<UserDTO> getAllAdmins();
}
