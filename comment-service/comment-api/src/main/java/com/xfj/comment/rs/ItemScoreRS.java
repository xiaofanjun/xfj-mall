package com.xfj.comment.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @author heps
 * @date 2019/8/18 22:31
 * 根据评价计算商品综合评分返回结果
 */
@Data
public class ItemScoreRS extends AbstractResponse {

    /**
     * 综合评分
     */
    private double score;
}
