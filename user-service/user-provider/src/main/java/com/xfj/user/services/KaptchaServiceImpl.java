package com.xfj.user.services;

import com.xfj.user.IKaptchaService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.entitys.ImageResult;
import com.xfj.user.rs.KaptchaCodeRS;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.utils.VerifyCodeUtils;
import com.xfj.user.vo.KaptchaCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZQ
 * @Description 验证码相关
 * @Date 2019/11/25 20:26
 **/
@Slf4j
@Service(group = "${dubbo-group.name}")
public class KaptchaServiceImpl implements IKaptchaService {

    @Autowired
    RedissonClient redissonClient;

    private final String KAPTCHA_UUID = "kaptcha_uuid";


    @Override
    public KaptchaCodeRS getKaptchaCode(KaptchaCodeVO request) {
        KaptchaCodeRS response = new KaptchaCodeRS();
        try {
            ImageResult capText = VerifyCodeUtils.VerifyCode(140, 43, 4);
            response.setImageCode(capText.getImg());
            String uuid = UUID.randomUUID().toString();
            //
            RBucket rBucket = redissonClient.getBucket(KAPTCHA_UUID + uuid);
            rBucket.set(capText.getCode());
            rBucket.expire(60, TimeUnit.SECONDS);
            response.setImageCode(capText.getImg());
            response.setUuid(uuid);
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("KaptchaServiceImpl.getKaptchaCode occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public KaptchaCodeRS validateKaptchaCode(KaptchaCodeVO request) {
        KaptchaCodeRS response = new KaptchaCodeRS();
        try {
            request.requestCheck();
            String redisKey = KAPTCHA_UUID + request.getUuid();
            RBucket<String> rBucket = redissonClient.getBucket(redisKey);
            String code = rBucket.get();
            log.info("请求的redisKey={},请求的code={},从redis获得的code={}", redisKey, request.getCode(), code);
            if (StringUtils.isNotBlank(code) && request.getCode().equalsIgnoreCase(code)) {
                response.setCode(SysRetCodeConstants.SUCCESS.getCode());
                response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
                return response;
            }
            response.setCode(SysRetCodeConstants.KAPTCHA_CODE_ERROR.getCode());
            response.setMsg(SysRetCodeConstants.KAPTCHA_CODE_ERROR.getMessage());
        } catch (Exception e) {
            log.error("KaptchaServiceImpl.validateKaptchaCode occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
