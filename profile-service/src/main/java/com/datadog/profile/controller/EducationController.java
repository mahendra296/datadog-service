package com.datadog.profile.controller;

import com.datadog.profile.model.Education;
import com.datadog.profile.service.EducationService;
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
@RequestMapping("/api/educations")
public class EducationController {

    private static final Logger log = LoggerFactory.getLogger(EducationController.class);

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<Education> createEducation(@RequestBody Education education) {
        log.info("REST request to create education for userId: {}", education.getUserId());
        try {
            Education createdEducation = educationService.createEducation(education);
            log.info("REST response - education created with id: {}", createdEducation.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEducation);
        } catch (IllegalArgumentException e) {
            log.error("REST error creating education: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Education> getEducationById(@PathVariable Long id) {
        log.info("REST request to get education by id: {}", id);
        return educationService
                .getEducationById(id)
                .map(education -> {
                    log.info("REST response - education found with id: {}", id);
                    return ResponseEntity.ok(education);
                })
                .orElseGet(() -> {
                    log.warn("REST response - education not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<Education>> getAllEducations(
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        log.info("REST request to get all educations - page: {}, size: {}", page, size);

        List<Education> educations;
        if (page != null && size != null) {
            educations = educationService.getAllEducations(page, size);
            log.info("REST response - returning {} educations (paginated)", educations.size());
        } else {
            educations = educationService.getAllEducations();
            log.info("REST response - returning {} educations", educations.size());
        }

        return ResponseEntity.ok(educations);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Education>> getEducationsByUserId(@PathVariable Long userId) {
        log.info("REST request to get educations by userId: {}", userId);
        List<Education> educations = educationService.getEducationsByUserId(userId);
        log.info("REST response - returning {} educations for userId: {}", educations.size(), userId);
        return ResponseEntity.ok(educations);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getEducationCount() {
        log.info("REST request to get education count");
        int count = educationService.getTotalCount();
        log.info("REST response - total educations: {}", count);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id, @RequestBody Education educationDetails) {
        log.info("REST request to update education with id: {}", id);
        try {
            Education updatedEducation = educationService.updateEducation(id, educationDetails);
            log.info("REST response - education updated successfully: {}", updatedEducation.getId());
            return ResponseEntity.ok(updatedEducation);
        } catch (IllegalArgumentException e) {
            log.error("REST error updating education: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.info("REST request to delete education with id: {}", id);
        try {
            educationService.deleteEducation(id);
            log.info("REST response - education deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("REST error deleting education: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteEducationsByUserId(@PathVariable Long userId) {
        log.info("REST request to delete all educations for userId: {}", userId);
        educationService.deleteEducationsByUserId(userId);
        log.info("REST response - all educations deleted for userId: {}", userId);
        return ResponseEntity.noContent().build();
    }
}
