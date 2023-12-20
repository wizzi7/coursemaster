package com.coursemaster.courseservice.authClient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = getUserToken();
        if (StringUtils.hasText(token))
            requestTemplate.header("Authorization", "Bearer " + token);
    }

    private String getUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if (authentication.getCredentials() != null)
                return authentication.getCredentials().toString();
        }
        return null;
    }
}