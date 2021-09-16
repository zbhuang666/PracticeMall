package com.zhs.zhsmall.controller;

import com.zhs.common.api.CommonResult;
import com.zhs.zhsmall.domain.CartPromotionItem;
import com.zhs.zhsmall.domain.SmsCouponHistoryDetail;
import com.zhs.zhsmall.model.SmsCouponHistory;
import com.zhs.zhsmall.service.UmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券管理Controller
 * Created by macro on 2018/8/29.
 */
@Controller
@Api(tags = "UmsCouponController", description = "用户优惠券管理")
@RequestMapping("/coupon")
public class UmsCouponController {
    @Autowired
    private UmsCouponService umsCouponService;

//    @Autowired
//    private OmsCartItemClientApi omsCartItemClientApi;


    @ApiOperation("领取指定优惠券")
    @RequestMapping(value = "/add/{couponId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@PathVariable("couponId") Long couponId,
                            @RequestHeader("memberId") Long memberId,
                            @RequestHeader("nickName") String nickName) {
        return umsCouponService.add(couponId,memberId,nickName);
    }


    @ApiOperation("获取用户优惠券列表")
    @ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCouponHistory>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus
                                                     , @RequestHeader("memberId") Long memberId) {
        if(memberId ==2){
            throw  new IllegalArgumentException("非法参数异常");
        }
        
        List<SmsCouponHistory> couponHistoryList = umsCouponService.list(useStatus,memberId);
        
        
        return CommonResult.success(couponHistoryList);
    }

//    @ApiOperation("获取登录会员购物车的相关优惠券")
//    @ApiImplicitParam(name = "type", value = "使用可用:0->不可用；1->可用",
//            defaultValue = "1", allowableValues = "0,1", paramType = "query", dataType = "integer")
//    @RequestMapping(value = "/list/cart/{type}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable Integer type,@RequestHeader("memberId") Long memberId) {
//        List<CartPromotionItem> cartPromotionItemList = omsCartItemClientApi.listPromotionByMemberId().getData();
//        List<SmsCouponHistoryDetail> couponHistoryList = umsCouponService.listCart(cartPromotionItemList, type,memberId);
//        return CommonResult.success(couponHistoryList);
//    }

    @RequestMapping(value = "/listCart", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<SmsCouponHistoryDetail>> listCart2Feign(@RequestParam Integer type,
                                                                     @RequestBody List<CartPromotionItem> cartPromotionItemList,
                                                                     @RequestHeader("memberId")Long memberId) {

        List<SmsCouponHistoryDetail> couponHistoryList = umsCouponService.listCart(cartPromotionItemList, type,memberId);
        return CommonResult.success(couponHistoryList);
    }
}
