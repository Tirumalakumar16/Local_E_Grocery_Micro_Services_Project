package com.customer.CustomerService.feignclients;

import com.identityservice.dtos.IdentityResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "Identity-service" ,path = "/www.localGrocery.com/identity/api")
public interface IdentityFeignClient {

    @GetMapping("/identity")
    public IdentityResponseDto getUserCredentials(@RequestHeader("LoggedInUser") String userName);
}
