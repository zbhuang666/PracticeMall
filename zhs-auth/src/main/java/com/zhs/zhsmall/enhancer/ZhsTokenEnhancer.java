package com.zhs.zhsmall.enhancer;

import com.zhs.zhsmall.domain.MemberDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class ZhsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        MemberDetails memberDetails = (MemberDetails)oAuth2Authentication.getPrincipal();
        Map<String, Object> additionInfo = new HashMap<>();
        Map<String, Object> retMap = new HashMap<>();

        additionInfo.put("memberId", memberDetails.getUmsMember().getId());
        additionInfo.put("nickName", memberDetails.getUmsMember().getNickname());
        additionInfo.put("integration", memberDetails.getUmsMember().getIntegration());

        retMap.put("additionalInfo", additionInfo);

        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(retMap);
        return oAuth2AccessToken;
    }
}
