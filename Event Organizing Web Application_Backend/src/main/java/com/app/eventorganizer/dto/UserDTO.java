package com.app.eventorganizer.dto;

import java.sql.Timestamp;

import com.app.eventorganizer.entity.Role;
import com.app.eventorganizer.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String mobileNo;
    private Long roleId;
    private String gstNo;
    private Integer bookingCapacity;
    private String specialization;
    private java.sql.Date dateOfBirth;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public UserDTO(User user) {
        this.userId = user.getUserId(); // Ensure all necessary fields are populated
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword(); // Be cautious with sensitive data
        this.address = user.getAddress();
        this.mobileNo = user.getMobileNo();
        this.gstNo = user.getGstNo();
        this.bookingCapacity = user.getBookingCapacity();
        this.specialization = user.getSpecialization();
        this.dateOfBirth = user.getDateOfBirth();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        if (user.getRole() != null) {
            this.roleId = user.getRole().getRoleId(); 
        }
    }



}
