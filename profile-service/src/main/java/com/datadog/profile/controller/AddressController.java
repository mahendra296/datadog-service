package com.datadog.profile.controller;

import com.datadog.profile.model.Address;
import com.datadog.profile.service.AddressService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        log.info("REST request to create address for userId: {}", address.getUserId());
        try {
            Address createdAddress = addressService.createAddress(address);
            log.info("REST response - address created with id: {}", createdAddress.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        } catch (IllegalArgumentException e) {
            log.error("REST error creating address: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        log.info("REST request to get address by id: {}", id);
        return addressService
                .getAddressById(id)
                .map(address -> {
                    log.info("REST response - address found with id: {}", id);
                    return ResponseEntity.ok(address);
                })
                .orElseGet(() -> {
                    log.warn("REST response - address not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses(
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        log.info("REST request to get all addresses - page: {}, size: {}", page, size);

        List<Address> addresses;
        if (page != null && size != null) {
            addresses = addressService.getAllAddresses(page, size);
            log.info("REST response - returning {} addresses (paginated)", addresses.size());
        } else {
            addresses = addressService.getAllAddresses();
            log.info("REST response - returning {} addresses", addresses.size());
        }

        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        log.info("REST request to get addresses by userId: {}", userId);
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        log.info("REST response - returning {} addresses for userId: {}", addresses.size(), userId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAddressCount() {
        log.info("REST request to get address count");
        int count = addressService.getTotalCount();
        log.info("REST response - total addresses: {}", count);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        log.info("REST request to update address with id: {}", id);
        try {
            Address updatedAddress = addressService.updateAddress(id, addressDetails);
            log.info("REST response - address updated successfully: {}", updatedAddress.getId());
            return ResponseEntity.ok(updatedAddress);
        } catch (IllegalArgumentException e) {
            log.error("REST error updating address: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.info("REST request to delete address with id: {}", id);
        try {
            addressService.deleteAddress(id);
            log.info("REST response - address deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("REST error deleting address: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAddressesByUserId(@PathVariable Long userId) {
        log.info("REST request to delete all addresses for userId: {}", userId);
        addressService.deleteAddressesByUserId(userId);
        log.info("REST response - all addresses deleted for userId: {}", userId);
        return ResponseEntity.noContent().build();
    }
}
