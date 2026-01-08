package com.datadog.profile.repository;

import com.datadog.profile.model.Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {

    private final Map<Long, Address> addresses = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Address save(Address address) {
        if (address.getId() == null) {
            address.setId(idGenerator.getAndIncrement());
        }
        addresses.put(address.getId(), address);
        return address;
    }

    public Optional<Address> findById(Long id) {
        return Optional.ofNullable(addresses.get(id));
    }

    public List<Address> findAll() {
        return new ArrayList<>(addresses.values());
    }

    public List<Address> findByUserId(Long userId) {
        return addresses.values().stream()
                .filter(address -> address.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Address> findAll(int page, int size) {
        List<Address> allAddresses = new ArrayList<>(addresses.values());
        int start = page * size;
        int end = Math.min(start + size, allAddresses.size());

        if (start >= allAddresses.size()) {
            return new ArrayList<>();
        }

        return allAddresses.subList(start, end);
    }

    public int count() {
        return addresses.size();
    }

    public boolean existsById(Long id) {
        return addresses.containsKey(id);
    }

    public void deleteById(Long id) {
        addresses.remove(id);
    }

    public void deleteByUserId(Long userId) {
        addresses.entrySet().removeIf(entry -> entry.getValue().getUserId().equals(userId));
    }
}
