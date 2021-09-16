package com.zhs.zhsmall.service;

import com.zhs.zhsmall.vo.ESRequestParam;
import com.zhs.zhsmall.vo.ESResponseResult;

public interface ZhsSearchService {

    ESResponseResult search(ESRequestParam param);
}
