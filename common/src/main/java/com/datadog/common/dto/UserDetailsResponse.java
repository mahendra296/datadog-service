package com.datadog.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active;
    private List<AddressDto> addresses;
    private List<EducationDto> educations;
}
