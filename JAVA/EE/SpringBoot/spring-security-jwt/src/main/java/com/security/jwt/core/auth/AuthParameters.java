package com.security.jwt.core.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:auth.properties")
public class AuthParameters {

    // 这里的过期时间和密钥配在properties文件里面，代码通过@Value拿的。
    private String jwtTokenSecret;
    private long tokenExpiredMs;

    public String getJwtTokenSecret() {
        return jwtTokenSecret;
    }

    @Value("${jwtTokenSecret}")
    public void setJwtTokenSecret(String jwtTokenSecret) {
        this.jwtTokenSecret = jwtTokenSecret;
    }

    public long getTokenExpiredMs() {
        return tokenExpiredMs;
    }

    @Value("${tokenExpiredMs}")
    public void setTokenExpiredMs(long tokenExpiredMs) {
        this.tokenExpiredMs = tokenExpiredMs;
    }
}
