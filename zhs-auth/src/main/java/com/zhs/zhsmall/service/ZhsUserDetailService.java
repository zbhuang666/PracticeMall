package com.zhs.zhsmall.service;

import com.zhs.common.api.CommonResult;
import com.zhs.zhsmall.domain.MemberDetails;
import com.zhs.zhsmall.model.UmsMember;
import com.zhs.zhsmall.feign.UmsMemberFeignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ZhsUserDetailService implements UserDetailsService {

    @Resource
    private UmsMemberFeignService umsMemberFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO  查数据库获取用户信息   rpc调用
        // 加载用户信息
        if (StringUtils.isEmpty(username)) {
            log.warn("用户登陆用户名为空:{}", username);
            throw new UsernameNotFoundException("用户名不能为空");
        }

        UmsMember umsMember = getByUsername(username);

        if (null == umsMember) {
            log.warn("根据用户名没有查询到对应的用户信息:{}", username);
        }

        log.info("根据用户名:{}获取用户登陆信息:{}", username, umsMember);

        // 会员信息的封装 implements UserDetails
        MemberDetails memberDetails = new MemberDetails(umsMember);

        return memberDetails;
    }

    public UmsMember getByUsername(String username) {
        // fegin获取会员信息
        CommonResult<UmsMember> umsMemberCommonResult = umsMemberFeignService.loadUserByUsername(username);

        return umsMemberCommonResult.getData();
    }

}
