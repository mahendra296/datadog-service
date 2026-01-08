package com.datadog.profile.service;

import com.datadog.profile.model.Education;
import com.datadog.profile.repository.EducationRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EducationService {

    private static final Logger log = LoggerFactory.getLogger(EducationService.class);

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Education createEducation(Education education) {
        log.info("Creating new education for userId: {}", education.getUserId());
        log.debug(
                "Education details - stream: {}, startDate: {}, endDate: {}, per: {}",
                education.getStream(),
                education.getStartDate(),
                education.getEndDate(),
                education.getPer());

        Education savedEducation = educationRepository.save(education);
        log.info("Education created successfully with id: {}", savedEducation.getId());
        return savedEducation;
    }

    public Optional<Education> getEducationById(Long id) {
        log.info("Fetching education with id: {}", id);
        Optional<Education> education = educationRepository.findById(id);

        if (education.isPresent()) {
            log.debug("Education found with id: {}", id);
        } else {
            log.warn("Education not found with id: {}", id);
        }

        return education;
    }

    public List<Education> getAllEducations() {
        log.info("Fetching all educations");
        List<Education> educations = educationRepository.findAll();
        log.debug("Found {} educations", educations.size());
        return educations;
    }

    public List<Education> getEducationsByUserId(Long userId) {
        log.info("Fetching educations for userId: {}", userId);
        List<Education> educations = educationRepository.findByUserId(userId);
        log.debug("Found {} educations for userId: {}", educations.size(), userId);
        return educations;
    }

    public List<Education> getAllEducations(int page, int size) {
        log.info("Fetching educations with pagination - page: {}, size: {}", page, size);
        List<Education> educations = educationRepository.findAll(page, size);
        log.debug("Found {} educations for page {}", educations.size(), page);
        return educations;
    }

    public int getTotalCount() {
        log.debug("Getting total education count");
        int count = educationRepository.count();
        log.debug("Total educations: {}", count);
        return count;
    }

    public Education updateEducation(Long id, Education educationDetails) {
        log.info("Updating education with id: {}", id);
        log.debug(
                "Update details - stream: {}, startDate: {}, endDate: {}, per: {}",
                educationDetails.getStream(),
                educationDetails.getStartDate(),
                educationDetails.getEndDate(),
                educationDetails.getPer());

        Optional<Education> existingEducation = educationRepository.findById(id);

        if (existingEducation.isEmpty()) {
            log.error("Education not found for update with id: {}", id);
            throw new IllegalArgumentException("Education not found with id: " + id);
        }

        Education education = existingEducation.get();
        education.setStream(educationDetails.getStream());
        education.setStartDate(educationDetails.getStartDate());
        education.setEndDate(educationDetails.getEndDate());
        education.setPer(educationDetails.getPer());
        education.setUserId(educationDetails.getUserId());

        Education updatedEducation = educationRepository.save(education);
        log.info("Education updated successfully with id: {}", updatedEducation.getId());
        return updatedEducation;
    }

    public void deleteEducation(Long id) {
        log.info("Deleting education with id: {}", id);

        if (!educationRepository.existsById(id)) {
            log.error("Education not found for deletion with id: {}", id);
            throw new IllegalArgumentException("Education not found with id: " + id);
        }

        educationRepository.deleteById(id);
        log.info("Education deleted successfully with id: {}", id);
    }

    public void deleteEducationsByUserId(Long userId) {
        log.info("Deleting all educations for userId: {}", userId);
        educationRepository.deleteByUserId(userId);
        log.info("All educations deleted successfully for userId: {}", userId);
    }

    public boolean existsById(Long id) {
        log.debug("Checking if education exists with id: {}", id);
        boolean exists = educationRepository.existsById(id);
        log.debug("Education exists: {}", exists);
        return exists;
    }
}
