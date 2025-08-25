package com.letrogthien.wallet.redis;

import com.letrogthien.wallet.otp.OtpModel;
import org.springframework.data.redis.core.RedisTemplate;

public class OtpRedisTemplate extends RedisTemplate<String, OtpModel> {
}
