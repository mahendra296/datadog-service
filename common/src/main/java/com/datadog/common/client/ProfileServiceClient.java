package com.datadog.common.client;

import com.datadog.common.dto.AddressDto;
import com.datadog.common.dto.EducationDto;
import com.datadog.common.feign.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "${profile-service.url}", configuration = FeignConfig.class)
public interface ProfileServiceClient {

    @GetMapping("/api/addresses/user/{userId}")
    List<AddressDto> getAddressesByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/educations/user/{userId}")
    List<EducationDto> getEducationsByUserId(@PathVariable("userId") Long userId);
}
