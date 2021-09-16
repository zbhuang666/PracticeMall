package com.zhs.zhsmall.controller;

import com.zhs.common.api.CommonResult;
import com.zhs.zhsmall.domain.PortalMemberInfo;
import com.zhs.zhsmall.feign.CouponsFeignService;
import com.zhs.zhsmall.model.UmsMember;
import com.zhs.zhsmall.service.UmsMemberCenterService;
import com.zhs.zhsmall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ,;,,; ,;;'(    社 __      ,;;' ' \   会 /'  '\'~~'~' \ /'\.)  主 ,;(      )    /  |.     义 ,;' \    /-.,,(   ) \    码 )
 * /       ) / )|    农 ||        ||  \) (_\       (_\
 *
 * @author ：杨过
 * @date ：Created in 2019/12/30 19:40
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description: 会员中心
 **/
@Api(tags = "UmsMemberCenterController", description = "会员中心管理操作#杨过添加")
@RestController
@RequestMapping("/member/center")
public class UmsMemberCenterController {
    
    @Autowired
    private UmsMemberCenterService umsMemberCenterService;
    
    @Autowired
    private UmsMemberService umsMemberService;
    
    /**
     * 关注店铺总数,收藏商品总数,近期7天浏览商品数,优惠券数量
     */
    @ApiOperation(value = "用户中心主页相关信息#功能未实现", notes = "关注店铺总数,收藏商品总数,近期7天浏览商品数,优惠券数量")
    @GetMapping("/userHome")
    public CommonResult<Map<String, String>> index() {
        return CommonResult.success(null);
    }
    
    @ApiOperation(value = "获取会员信息#杨过添加", notes = "不包含会员等级信息,会员需要被拆分成微服务")
    @GetMapping("/loadUmsMember")
    public CommonResult<UmsMember> loadUserByUsername(String username) {
        UmsMember umsMember = umsMemberService.getByUsername(username);
        if (umsMember == null) {
            return CommonResult.failed("会员不存在或者已经被禁用");
        }
        return CommonResult.success(umsMember);
    }
    
    @ApiOperation(value = "获取会员详细信息包含会员等级信息#杨过添加", notes = "会员需要被拆分成微服务")
    @GetMapping("/getMemberInfo")
    public CommonResult<PortalMemberInfo> getMemberInfo(@RequestHeader("memberId") long memberId) {
        return CommonResult.success(umsMemberCenterService.getMemberInfo(memberId));
    }
    
    @ApiOperation(value = "修改个人信息#杨过添加", notes = "会员需要被拆分成微服务")
    @RequestMapping(value = "updateUmsMember", method = RequestMethod.POST)
    public CommonResult<String> updateUmsMember(@RequestBody UmsMember umsMember,
            @RequestHeader("memberId") long memberId) {
        if (!StringUtils.isEmpty(umsMember.getPassword())) {
            return CommonResult.validateFailed("仅限修改资料,不能修改密码！");
        }
        //从网关解析jwt后 把memberId存到请求头中
        umsMember.setId(memberId);
        
        if (umsMemberService.updateUmsMember(umsMember) > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }
    
    
    //TODO  根据会员id调优惠券信息
    @Autowired
    private CouponsFeignService couponsFeignService;

    @RequestMapping(value = "/coupons", method = RequestMethod.GET)
    public CommonResult getCoupons(@RequestParam(value = "useStatus", required = false) Integer useStatus,
            @RequestHeader("memberId") Long memberId) {
        // 通过openfeign从远程微服务zhs-coupons获取优惠券信息
        return couponsFeignService.list(useStatus, memberId);
    }
    
}
