package com.zhs.zhsmall.feign;

import com.zhs.common.api.CommonResult;
import com.zhs.zhsmall.model.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "zhs-member",path="/member/center")
public interface UmsMemberFeignService {
    
    @RequestMapping("/loadUmsMember")
    CommonResult<UmsMember> loadUserByUsername(@RequestParam("username") String username);
}