package com.datadog.profile.repository;

import com.datadog.profile.model.Education;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class EducationRepository {

    private final Map<Long, Education> educations = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Education save(Education education) {
        if (education.getId() == null) {
            education.setId(idGenerator.getAndIncrement());
        }
        educations.put(education.getId(), education);
        return education;
    }

    public Optional<Education> findById(Long id) {
        return Optional.ofNullable(educations.get(id));
    }

    public List<Education> findAll() {
        return new ArrayList<>(educations.values());
    }

    public List<Education> findByUserId(Long userId) {
        return educations.values().stream()
                .filter(education -> education.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Education> findAll(int page, int size) {
        List<Education> allEducations = new ArrayList<>(educations.values());
        int start = page * size;
        int end = Math.min(start + size, allEducations.size());

        if (start >= allEducations.size()) {
            return new ArrayList<>();
        }

        return allEducations.subList(start, end);
    }

    public int count() {
        return educations.size();
    }

    public boolean existsById(Long id) {
        return educations.containsKey(id);
    }

    public void deleteById(Long id) {
        educations.remove(id);
    }

    public void deleteByUserId(Long userId) {
        educations.entrySet().removeIf(entry -> entry.getValue().getUserId().equals(userId));
    }
}
