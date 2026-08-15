package com.heroku.java.controller;

import com.heroku.java.model.Customer;
import com.heroku.java.model.Staff;
import com.heroku.java.repository.CustomerRepository;
import com.heroku.java.repository.StaffRepository;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ImageController {

    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;

    public ImageController(CustomerRepository customerRepository, StaffRepository staffRepository) {
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
    }

    @GetMapping("/image/customer/{custId}")
    public ResponseEntity<byte[]> customerImage(@PathVariable Long custId) {
        Optional<Customer> customer = customerRepository.findById(custId);
        if (customer.isEmpty() || customer.get().getCustPictureData() == null) {
            return ResponseEntity.notFound().build();
        }
        Customer c = customer.get();
        MediaType mediaType = MediaTypeFactory.getMediaType(c.getCustPicture() != null ? c.getCustPicture() : "")
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().contentType(mediaType).body(c.getCustPictureData());
    }

    @GetMapping("/image/staff/{staffId}")
    public ResponseEntity<byte[]> staffImage(@PathVariable Long staffId) {
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (staff.isEmpty() || staff.get().getStaffPictureData() == null) {
            return ResponseEntity.notFound().build();
        }
        Staff s = staff.get();
        MediaType mediaType = MediaTypeFactory.getMediaType(s.getStaffPicture() != null ? s.getStaffPicture() : "")
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().contentType(mediaType).body(s.getStaffPictureData());
    }
}