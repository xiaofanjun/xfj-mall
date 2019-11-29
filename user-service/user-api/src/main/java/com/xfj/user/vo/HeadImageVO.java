package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import lombok.Data;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:08
 **/
@Data
public class HeadImageVO extends AbstractRequest {
    private Long userId;
    private String imageData;

    @Override
    public void requestCheck() {

    }
}
