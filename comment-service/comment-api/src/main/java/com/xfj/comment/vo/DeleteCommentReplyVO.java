package com.xfj.comment.vo;

import com.xfj.comment.constant.CommentRetCode;
import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heps
 * @date 2019/8/22 23:03
 * 删除商品评价回复意见请求参数
 */
@Data
public class DeleteCommentReplyVO extends AbstractRequest {

    /**
     * 回复意见id
     */
    private String commentReplyId;

    /**
     * 删除人id
     */
    private String userId;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(commentReplyId) || null == userId) {
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
