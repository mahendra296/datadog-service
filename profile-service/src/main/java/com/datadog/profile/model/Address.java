package com.datadog.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    private String address1;
    private String address2;
    private String area;
    private String city;
    private String pincode;
    private Long userId;
}
