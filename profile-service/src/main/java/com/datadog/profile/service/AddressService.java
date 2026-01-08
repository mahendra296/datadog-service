package com.datadog.profile.service;

import com.datadog.profile.model.Address;
import com.datadog.profile.repository.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address) {
        log.info("Creating new address for userId: {}", address.getUserId());
        log.debug(
                "Address details - address1: {}, city: {}, pincode: {}",
                address.getAddress1(),
                address.getCity(),
                address.getPincode());

        Address savedAddress = addressRepository.save(address);
        log.info("Address created successfully with id: {}", savedAddress.getId());
        return savedAddress;
    }

    public Optional<Address> getAddressById(Long id) {
        log.info("Fetching address with id: {}", id);
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()) {
            log.debug("Address found with id: {}", id);
        } else {
            log.warn("Address not found with id: {}", id);
        }

        return address;
    }

    public List<Address> getAllAddresses() {
        log.info("Fetching all addresses");
        List<Address> addresses = addressRepository.findAll();
        log.debug("Found {} addresses", addresses.size());
        return addresses;
    }

    public List<Address> getAddressesByUserId(Long userId) {
        log.info("Fetching addresses for userId: {}", userId);
        List<Address> addresses = addressRepository.findByUserId(userId);
        log.debug("Found {} addresses for userId: {}", addresses.size(), userId);
        return addresses;
    }

    public List<Address> getAllAddresses(int page, int size) {
        log.info("Fetching addresses with pagination - page: {}, size: {}", page, size);
        List<Address> addresses = addressRepository.findAll(page, size);
        log.debug("Found {} addresses for page {}", addresses.size(), page);
        return addresses;
    }

    public int getTotalCount() {
        log.debug("Getting total address count");
        int count = addressRepository.count();
        log.debug("Total addresses: {}", count);
        return count;
    }

    public Address updateAddress(Long id, Address addressDetails) {
        log.info("Updating address with id: {}", id);
        log.debug(
                "Update details - address1: {}, city: {}, pincode: {}",
                addressDetails.getAddress1(),
                addressDetails.getCity(),
                addressDetails.getPincode());

        Optional<Address> existingAddress = addressRepository.findById(id);

        if (existingAddress.isEmpty()) {
            log.error("Address not found for update with id: {}", id);
            throw new IllegalArgumentException("Address not found with id: " + id);
        }

        Address address = existingAddress.get();
        address.setAddress1(addressDetails.getAddress1());
        address.setAddress2(addressDetails.getAddress2());
        address.setArea(addressDetails.getArea());
        address.setCity(addressDetails.getCity());
        address.setPincode(addressDetails.getPincode());
        address.setUserId(addressDetails.getUserId());

        Address updatedAddress = addressRepository.save(address);
        log.info("Address updated successfully with id: {}", updatedAddress.getId());
        return updatedAddress;
    }

    public void deleteAddress(Long id) {
        log.info("Deleting address with id: {}", id);

        if (!addressRepository.existsById(id)) {
            log.error("Address not found for deletion with id: {}", id);
            throw new IllegalArgumentException("Address not found with id: " + id);
        }

        addressRepository.deleteById(id);
        log.info("Address deleted successfully with id: {}", id);
    }

    public void deleteAddressesByUserId(Long userId) {
        log.info("Deleting all addresses for userId: {}", userId);
        addressRepository.deleteByUserId(userId);
        log.info("All addresses deleted successfully for userId: {}", userId);
    }

    public boolean existsById(Long id) {
        log.debug("Checking if address exists with id: {}", id);
        boolean exists = addressRepository.existsById(id);
        log.debug("Address exists: {}", exists);
        return exists;
    }
}
