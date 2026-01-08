package com.datadog.profile.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private Long id;
    private String stream;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double per;
    private Long userId;
}
