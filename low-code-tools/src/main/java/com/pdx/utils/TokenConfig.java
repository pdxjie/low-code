package com.pdx.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class TokenConfig {

    private String secretKey;
    private Duration accessTokenExpireTime;
    private String issuer;
}
