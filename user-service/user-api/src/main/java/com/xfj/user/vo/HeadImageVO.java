package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;

/**
 * @Author ZQ
 * @Description 上传图片对应的请求参数信息
 * @Date 2019/11/27 21:08
 **/
@Data
public class HeadImageVO extends AbstractRequest {
    private String userId;
    private String imageData;//图片的二进制文件
    private String token;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(imageData)) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
