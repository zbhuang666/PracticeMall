package com.zhs.zhsmall.feign;

import com.zhs.common.api.CommonResult;
import com.zhs.zhsmall.model.SmsCouponHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Fox
 */
@FeignClient(name = "zhs-coupons",path = "/coupon")
public interface CouponsFeignService {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    CommonResult<List<SmsCouponHistory>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus
            , @RequestHeader("memberId") Long memberId);
}
