package com.zhs.zhsmall.controller;

import com.tuling.tulingmall.common.api.CommonResult;
import com.zhs.zhsmall.service.ZhsSearchService;
import com.zhs.zhsmall.vo.ESRequestParam;
import com.zhs.zhsmall.vo.ESResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ZhsSearchController {

    @Resource
    ZhsSearchService zhsSearchService;

    /**
     * 自动将页面提交过来的所有请求参数封装成我们指定的对象
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchList")
    public CommonResult<ESResponseResult> listPage(ESRequestParam param, HttpServletRequest request) {

        //1、根据传递来的页面的查询参数，去es中检索商品
        ESResponseResult searchResult = zhsSearchService.search(param);

        return CommonResult.success(searchResult);
    }

}
