package com.letrogthien.user.redis;

import com.letrogthien.user.otp.OtpModel;
import org.springframework.data.redis.core.RedisTemplate;

public class OtpRedisTemplate extends RedisTemplate<String, OtpModel> {
}
