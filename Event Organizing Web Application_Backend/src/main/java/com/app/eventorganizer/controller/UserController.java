package com.app.eventorganizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.eventorganizer.dto.AuthRequestDTO;
import com.app.eventorganizer.dto.AuthResponseDTO;
import com.app.eventorganizer.dto.UserDTO;
import com.app.eventorganizer.service.UserService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    

    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @GetMapping("/vendors")
    public ResponseEntity<List<UserDTO>> getAllVendors() {
        List<UserDTO> vendors = userService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    
    @GetMapping("/vendors/{id}")
    public ResponseEntity<UserDTO> getVendorById(@PathVariable Long id) {
        UserDTO vendor = userService.getVendorById(id);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    
    @PutMapping("/vendors/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateVendor(id, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        userService.deleteVendor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @GetMapping("/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
        List<UserDTO> customers = userService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    
    @GetMapping("/customers/{id}")
    public ResponseEntity<UserDTO> getCustomerById(@PathVariable Long id) {
        UserDTO customer = userService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    
    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateCustomer(id, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        userService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @GetMapping("/admins")
    public ResponseEntity<List<UserDTO>> getAllAdmins() {
        List<UserDTO> admins = userService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
}
